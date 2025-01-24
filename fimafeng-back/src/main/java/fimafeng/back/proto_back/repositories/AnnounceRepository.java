package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
}