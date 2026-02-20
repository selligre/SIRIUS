package fr.upec.episen.sirius.fimafeng.repositories;

import fr.upec.episen.sirius.fimafeng.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameEquals(String username);
}
