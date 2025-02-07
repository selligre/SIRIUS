package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.AnnounceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnounceTagRepository extends JpaRepository<AnnounceTag, Integer> {
}
