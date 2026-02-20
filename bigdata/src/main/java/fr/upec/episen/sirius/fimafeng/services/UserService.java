package fr.upec.episen.sirius.fimafeng.services;

import fr.upec.episen.sirius.fimafeng.models.User;
import fr.upec.episen.sirius.fimafeng.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return UserRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsernameEquals(username);
        return optionalUser.orElse(null);
    }




}
