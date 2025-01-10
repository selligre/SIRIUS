package fimafeng.back.proto_back.implementations.profiles;

import fimafeng.back.proto_back.mocks.ClientFactory;
import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.services.ClientService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientProfileImplementation extends ClientService {

    private final Logger LOGGER = Logger.getLogger(ClientFactory.class.getName());

    public ClientProfileImplementation() {
        super();
        this.getClientsData();
    }

    private void getClientsData() {
        LOGGER.log(Level.FINE, "getClientsData started");
        ClientService clientService = new ClientService();
        for (Client client : clientService.findAll()) {
            LOGGER.log(Level.FINER, client.toString());
        }
        LOGGER.log(Level.FINE, "getClientsData finished");
    }

}
