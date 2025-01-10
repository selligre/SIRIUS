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
        LOGGER.info("clients:");
        ArrayList<Client> clients = new ArrayList<>(clientService.findAll());
        LOGGER.info(clients.toString());
        LOGGER.info("clients_tags:");
        ArrayList<ClientTag> clientsTags = new ArrayList<>(clientTagService.findAll());
        LOGGER.info(clientsTags.toString());
    }


}
