package fimafeng.back.fimafeng_back.controllers;

import fimafeng.back.fimafeng_back.implementations.mocks.ClientFactory;
import fimafeng.back.fimafeng_back.implementations.profiles.ClientProfile;
import fimafeng.back.fimafeng_back.implementations.profiles.ClientProfileImplementation;
import fimafeng.back.fimafeng_back.implementations.recommendations.RecommendationImplementationPOC;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.Client;
import fimafeng.back.fimafeng_back.models.ClientTag;
import fimafeng.back.fimafeng_back.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("client")
public class ClientController {
    Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientTagService clientTagService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AnnounceTagService announceTagService;
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private AnnounceService announceService;

    @GetMapping("/id")
    public ResponseEntity<Client> findClientById(@RequestParam("id") int id) {
        LOGGER.info("findClientById()");
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        LOGGER.info("addClient()");
        Client createdClient = clientService.save(client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Client>> findAllClients() {
        LOGGER.info("findAll()");
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<Page<Client>> findAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LOGGER.info("findAll()");
        return new ResponseEntity<>(clientService.findSearch(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        LOGGER.info("updateClient()");
        boolean isUpdated = clientService.update(client);
        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable int id) {
        LOGGER.info("deleteClient()");
        boolean isRemoved = clientService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("profiles")
    public ResponseEntity<List<ClientProfile>> buildClientProfiles() {
        LOGGER.info("buildClientProfiles()");
        ClientProfileImplementation clientProfileImplementation = new ClientProfileImplementation(clientService, clientTagService, consultationService);
        return new ResponseEntity<>(clientProfileImplementation.buildClientProfile(), HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<String> generateClient(@RequestParam(required = false) Integer amount, @RequestParam(required = false) Integer delay) {
        LOGGER.info("generateClient()");
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

    @GetMapping("recommendations/{id}")
    public ResponseEntity<List<Announce>> buildClientRecommendations(@PathVariable int id) {
        LOGGER.info("buildClientRecommendations()");
        RecommendationImplementationPOC recommendationImplementationPOC = new RecommendationImplementationPOC(clientTagService, announceService, consultationService, announceTagService, tagService);
        return new ResponseEntity<>(recommendationImplementationPOC.generateRecommendations(id), HttpStatus.OK);
    }

    @GetMapping("{clientId}/announces")
    public ResponseEntity<Page<Announce>> buildClientAnnounces(
            @PathVariable int clientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LOGGER.info("findAllAnnouncesByClientId()");
        return new ResponseEntity<>(announceService.findAllAnnouncesByClientId(clientId, PageRequest.of(page, size)), HttpStatus.OK);
    }

}
