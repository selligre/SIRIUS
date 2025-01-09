package fimafeng.back.proto_back.implementations.profiles;

import fimafeng.back.proto_back.mocks.UserFactory;
import fimafeng.back.proto_back.models.User;
import fimafeng.back.proto_back.services.UserService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProfileImplementation extends UserService {

    private final Logger LOGGER = Logger.getLogger(UserFactory.class.getName());

    public UserProfileImplementation() {

    }
    
    private void getUsersData() {
        LOGGER.log(Level.FINE, "getUsersData started");
        UserService userService = new UserService();
        userService.findAll();
        for (User user : userService.findAll()) {
            LOGGER.log(Level.FINER, user.toString());
        }
        LOGGER.log(Level.FINE, "getUsersData finished");
    }

    public static void main(String[] args) {
        UserProfileImplementation userProfileImplementation = new UserProfileImplementation();
        userProfileImplementation.getUsersData();
    }
}
