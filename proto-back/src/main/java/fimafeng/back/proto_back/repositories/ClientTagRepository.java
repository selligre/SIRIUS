package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.ClientTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientTagRepository extends JpaRepository<ClientTag, Integer> {
}
