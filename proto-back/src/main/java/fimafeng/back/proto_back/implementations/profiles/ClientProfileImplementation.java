package fimafeng.back.proto_back.implementations.profiles;

import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.models.ClientTag;
import fimafeng.back.proto_back.services.ClientService;
import fimafeng.back.proto_back.services.ClientTagService;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientProfileImplementation extends ClientService {

    private final Logger LOGGER = Logger.getLogger(ClientProfileImplementation.class.getName());

    private final ClientService clientService;
    private final ClientTagService clientTagService;

    public ClientProfileImplementation(ClientService clientService, ClientTagService clientTagService) {
        super();
        this.clientService = clientService;
        this.clientTagService = clientTagService;

        this.getClientsData();
    }

    private void getClientsData() {
        // Retrieve clients' tags
        ArrayList<ClientTag> clientTags = new ArrayList<>(clientTagService.findAll());
        // Create data structure
        ArrayList<Object> clientProfiles = new ArrayList<>();
        // Retrieve data for the first 10 users
        for (int i = 1; i < 11; i++) {
            ArrayList<Integer> clientProfile = new ArrayList<>();
            Client client = clientService.findById(i);
            // Retrieve and store client's id
            clientProfile.add(client.getId());
            // Retrieve and store client's district id
            clientProfile.add(client.getDistrict());
            for (ClientTag clientTag : clientTags) {
                if (clientTag.getRefClientId() == client.getId()) {
                    // Retrieve and store client's tags
                    clientProfile.add(clientTag.getRefTagId());
                }
            }
            clientProfiles.add(clientProfile);
        }
        // Display the first 10 clients' profiles in logs
        LOGGER.info("clientProfiles: " + clientProfiles);
    }

}
