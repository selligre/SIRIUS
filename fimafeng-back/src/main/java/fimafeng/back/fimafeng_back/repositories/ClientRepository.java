package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
