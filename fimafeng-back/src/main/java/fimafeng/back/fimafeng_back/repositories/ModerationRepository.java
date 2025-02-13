package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Moderation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModerationRepository extends JpaRepository<Moderation, Integer> {
}