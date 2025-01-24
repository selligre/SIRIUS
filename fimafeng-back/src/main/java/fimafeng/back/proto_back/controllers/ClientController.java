package fimafeng.back.proto_back.controllers;

import fimafeng.back.proto_back.implementations.mocks.ClientFactory;
import fimafeng.back.proto_back.implementations.profiles.ClientProfileImplementation;
import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.models.ClientTag;
import fimafeng.back.proto_back.services.ClientService;
import fimafeng.back.proto_back.services.ClientTagService;
import fimafeng.back.proto_back.services.DistrictService;
import fimafeng.back.proto_back.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientTagService clientTagService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private TagService tagService;

    @GetMapping("/id")
    public ResponseEntity<Client> getClient(@RequestParam("id") int id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client createdClient = clientService.save(client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Client>> findAll() {
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Client> update(@RequestBody Client client) {
        boolean isUpdated = clientService.update(client);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable int id) {
        boolean isRemoved = clientService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("profile")
    public ResponseEntity<String> buildClientProfiles() {
        ClientProfileImplementation clientProfileImplementation = new ClientProfileImplementation(clientService, clientTagService);
        return new ResponseEntity<>(clientProfileImplementation.getClientsData(), HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<String> generateClient(@RequestParam(required = false) Integer amount, @RequestParam(required = false) Integer delay) {
        if (amount == null) {
            amount = 1;
        }
        if (delay == null) {
            delay = 0;
        }

        boolean error = false;
        int savedAmount = 0;

        for (int i = 0; i < amount; i++) {
            // Generates a client
            ClientFactory cf = new ClientFactory(districtService, tagService);
            Client generatedClient;
            try {
                generatedClient = clientService.save(cf.generateClient());
            } catch (IllegalArgumentException e) {
                error = true;
                break;
            }

            // Generates client's tags
            List<ClientTag> clientTags = cf.generateClientTags(generatedClient.getId());
            try {
                clientTagService.saveAll(clientTags);
            } catch (IllegalArgumentException e) {
                error = true;
                break;
            }
            savedAmount += 1;

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                error = true;
                break;
            }
        }

        // If error occurred, then returning Http Error and amount generated
        if (error) {
            String msg = String.format("Error: %d / %d", savedAmount, amount);
            return new ResponseEntity<>(msg, HttpStatus.EXPECTATION_FAILED);
        }

        // Otherwise, returning Http Success and amount generated
        String msg = String.format("Success: %d / %d", savedAmount, amount);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

}
