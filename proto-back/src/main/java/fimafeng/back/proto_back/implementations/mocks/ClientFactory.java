package fimafeng.back.proto_back.implementations.mocks;

import com.github.javafaker.Faker;
import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.models.District;
import fimafeng.back.proto_back.services.DistrictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Service
public class ClientFactory extends Client {

    Logger LOGGER = Logger.getLogger(ClientFactory.class.getName());

    private static List<Integer> districtsListWeighted;


    public ClientFactory(DistrictService districtService) {
        LOGGER.info("Initializing clientFactory");
        if (districtsListWeighted == null || districtsListWeighted.isEmpty()) {

            districtsListWeighted = new ArrayList<>();
            // Getting districts data from DB
            LOGGER.info("Retriving districts data");
            List<District> districtsList = districtService.findAll();

            // Generating a list weighted from district data
            LOGGER.info("Generating districts weights");

            for (District district : districtsList) {
                for (int i = 0 ; i < district.getPopulationPercentile() ; i++) {
                    districtsListWeighted.add(district.getId());
                }
            }

            LOGGER.info("Data generated: " + districtsListWeighted.toString());

        } else {
            LOGGER.info("Districts already initialized");
        }

    }


    public Client generate() {
        LOGGER.info("Generating random client");
        Faker faker = new Faker(new Locale("fr-FR"));
        Client client = new Client();
        client.setFirstName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        String email = client.getFirstName();
        switch (faker.random().nextInt(3)) {
            case 0 -> email += '-';
            case 1 -> email += '.';
            default -> email += "";
        }
        email+=client.getLastName();
        if (faker.random().nextBoolean()) {
            email += faker.random().nextInt(100);
        }
        email+='@';
        email += faker.internet().domainName();
        client.setEmail(email.toLowerCase());
        client.setDistrict(districtsListWeighted.get(faker.random().nextInt(districtsListWeighted.size())));
        LOGGER.info("Random client generated: " + client);
        return client;

    }
}