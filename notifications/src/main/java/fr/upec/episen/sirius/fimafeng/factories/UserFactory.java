package fr.upec.episen.sirius.fimafeng.factories;

import org.springframework.stereotype.Service;

import fr.upec.episen.sirius.fimafeng.commons.models.User;

import java.util.logging.Logger;

@Service
public class UserFactory {

    static Logger LOGGER = Logger.getLogger(UserFactory.class.getName());

    public User createUser() {
        LOGGER.info("Creating user");
        User user = new User();
        user.setUsername("");
        return user;
    }

}
