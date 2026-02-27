package fr.upec.episen.sirius.fimafeng.announce_manager.controllers;

import fr.upec.episen.sirius.fimafeng.commons.models.User;
import fr.upec.episen.sirius.fimafeng.announce_manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify/{username}")
    public ResponseEntity<Integer> findByUsernameEquals(@PathVariable String username) {

        LOGGER.info("Received GET /verify/"+username);
        Optional<User> user = userRepository.findByUsernameEquals(username);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get().getId());
        }
        return ResponseEntity.notFound().build();
    }

}
