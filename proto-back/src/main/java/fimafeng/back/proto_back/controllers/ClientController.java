package fimafeng.back.proto_back.controllers;

import fimafeng.back.proto_back.implementations.mocks.ClientFactory;
import fimafeng.back.proto_back.implementations.profiles.ClientProfileImplementation;
import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.services.ClientService;
import fimafeng.back.proto_back.services.ClientTagService;
import fimafeng.back.proto_back.services.DistrictService;
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

    @GetMapping("profiles")
    public ResponseEntity<Client> buildClientProfiles() {
        new ClientProfileImplementation(clientService, clientTagService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<Client> generateClient() {
        ClientFactory cf = new ClientFactory(districtService);
        Client generatedClient = clientService.save(cf.generate());
        return new ResponseEntity<>(generatedClient, HttpStatus.CREATED);
    }

}
