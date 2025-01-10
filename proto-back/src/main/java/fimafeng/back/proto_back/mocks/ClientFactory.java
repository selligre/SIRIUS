package fimafeng.back.proto_back.mocks;

import com.github.javafaker.Faker;
import fimafeng.back.proto_back.models.Client;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;


public class ClientFactory extends Client {

    Logger LOGGER = Logger.getLogger(ClientFactory.class.getName());

    Faker FAKER = new Faker(new Locale("fr-FR"));

    List<Integer> distritsIdsList;

    public ClientFactory() {

    }


    public Client create() {
        LOGGER.info("Creating client");
        Client client = new Client();
        client.setFirstName(FAKER.name().firstName());
        client.setLastName(FAKER.name().lastName());
        client.setEmail(FAKER.internet().emailAddress());

        // definition temporaire en attendant d'avoir des données en base
        // TODO: definir à partir de la table `location` quand elle aura été populée
        client.setDistrict(FAKER.number().randomDigit());
        LOGGER.info("Client created: " + client.toString());
        return client;

    }
}