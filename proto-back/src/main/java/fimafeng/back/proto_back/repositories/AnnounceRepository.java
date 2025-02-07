package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Announce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
    Page<Announce> findByRefLocationId(int refLocationId, Pageable pageable);
}