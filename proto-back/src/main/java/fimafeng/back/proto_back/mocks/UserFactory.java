package fimafeng.back.proto_back.mocks;

import com.github.javafaker.Faker;
import fimafeng.back.proto_back.models.User;

import java.util.Locale;
import java.util.logging.Logger;


public class UserFactory extends User {

    Logger LOGGER = Logger.getLogger(UserFactory.class.getName());

    Faker FAKER = new Faker(new Locale("fr-FR"));




    public User create() {
        LOGGER.info("Creating user");
        User user = new User();
        user.setFirstName(FAKER.name().firstName());
        user.setLastName(FAKER.name().lastName());
        user.setEmail(FAKER.internet().emailAddress());

        // definition temporaire en attendant d'avoir des données en base
        // TODO: definir à partir de la table `location` quand elle aura été populée
        user.setDistrict(FAKER.number().randomDigit());
        LOGGER.info("User created: " + user.toString());
        return user;

    }
}