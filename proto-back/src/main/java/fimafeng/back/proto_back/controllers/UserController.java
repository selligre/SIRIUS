package fimafeng.back.proto_back.controllers;

import fimafeng.back.proto_back.mocks.UserFactory;
import fimafeng.back.proto_back.models.User;
import fimafeng.back.proto_back.repositories.UserRepository;
import fimafeng.back.proto_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    private final UserFactory userFactory = new UserFactory();

    @GetMapping("/generate")
    public ResponseEntity<User> generate() {
        User generatedUser = userService.save(userFactory.generate());
        return new ResponseEntity<>(generatedUser, HttpStatus.CREATED);
    }
}
