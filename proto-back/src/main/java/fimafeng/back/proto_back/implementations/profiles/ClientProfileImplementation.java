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
        // Retrieve clients' data
        // LOGGER.info("clients:");
        ArrayList<Client> clients = new ArrayList<>(clientService.findAll());
        // LOGGER.info(clients.toString());

        // Retrieve clients' tags
        // LOGGER.info("clients_tags:");
        ArrayList<ClientTag> clientsTags = new ArrayList<>(clientTagService.findAll());
        // LOGGER.info(clientsTags.toString());

        // Assemble all user data to {client_id, district_id, ref_tag_id1, ref_tag_id2, etc.} format
        ArrayList<ArrayList<Integer>> clientProfiles = new ArrayList<>();
        for (Client client : clients) {
            // Creating data structure
            ArrayList<Integer> clientProfilesIn = getProfiles(client, clientsTags);
            clientProfiles.add(clientProfilesIn);
        }
        LOGGER.info("clientProfiles: " + clientProfiles);
    }

    private static ArrayList<Integer> getProfiles(Client client, ArrayList<ClientTag> clientsTags) {
        ArrayList<Integer> clientProfilesIn = new ArrayList<>();
        // Adding client's id
        clientProfilesIn.add(client.getId());
        // Adding client's district id
        clientProfilesIn.add(client.getDistrict());
        // Adding client's tags
        for (ClientTag clientTag : clientsTags) {
            if (clientTag.getRefClientId() == client.getId()) {
                clientProfilesIn.add(clientTag.getRefTagId());
            }
        }
        return clientProfilesIn;
    }
}
