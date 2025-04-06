package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "select c from Client c")
    Page<Client> findAllClient(Pageable pageable);
}
