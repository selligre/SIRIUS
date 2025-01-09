package fimafeng.back.proto_back.mocks;

import com.github.javafaker.Faker;
import fimafeng.back.proto_back.models.District;
import fimafeng.back.proto_back.models.User;
import fimafeng.back.proto_back.services.DistrictService;

import java.util.*;
import java.util.logging.Logger;

public class UserFactory extends User {

    private final Logger LOGGER = Logger.getLogger(UserFactory.class.getName());

    private final Faker FAKER = new Faker(new Locale("fr-FR"));

    List<Integer> districtsListWeighted;

    public UserFactory() {
        districtsListWeighted = new ArrayList<>();

        // Getting districts data from DB
        DistrictService districtService = new DistrictService();
        List<District> districtsList = districtService.findAll();

        // Generating a list weighted from district data
        for (District district : districtsList) {
            for (int i = 0 ; i < district.getPopulationPercentile() ; i++) {
                districtsListWeighted.add(district.getId());
            }
        }

    }


    public User generate() {
        LOGGER.info("Creating user");
        User user = new User();
        user.setFirstName(FAKER.name().firstName());
        user.setLastName(FAKER.name().lastName());
        user.setEmail(FAKER.internet().emailAddress());
        user.setDistrict(districtsListWeighted.get(FAKER.random().nextInt(0, districtsListWeighted.size())));
        LOGGER.info("User created: " + user.toString());
        return user;

    }
}