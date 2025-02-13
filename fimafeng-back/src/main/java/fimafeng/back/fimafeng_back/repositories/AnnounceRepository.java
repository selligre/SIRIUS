package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Announce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
    Page<Announce> findByRefLocationId(int refLocationId, Pageable pageable);
}