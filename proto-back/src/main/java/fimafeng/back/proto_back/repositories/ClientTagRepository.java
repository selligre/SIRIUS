package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.ClientTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientTagRepository extends JpaRepository<ClientTag, Integer> {
    //public int saveAll(List<ClientTag> clientTags);
}
