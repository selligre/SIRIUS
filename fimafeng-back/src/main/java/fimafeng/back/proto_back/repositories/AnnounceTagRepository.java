package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.AnnounceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnounceTagRepository extends JpaRepository<AnnounceTag, Integer> {
}
