package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.User;
import fimafeng.back.proto_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(int idUser) {
        Optional<User> user = userRepository.findById(idUser);
        return user.orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean update(User updatedUser) {
        if (updatedUser == null) throw new IllegalArgumentException("user is null");
        int id = updatedUser.getId();

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setDistrict(updatedUser.getDistrict());

        userRepository.saveAndFlush(user);
        return true;
    }

    public boolean delete(int idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isPresent()) {
            userRepository.deleteById(idUser);
            return true;
        }
        return false;
    }
}
