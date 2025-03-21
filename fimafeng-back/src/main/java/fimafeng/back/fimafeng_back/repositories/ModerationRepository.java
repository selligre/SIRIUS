package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Moderation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModerationRepository extends JpaRepository<Moderation, Integer> {
    // SELECT * FROM moderation WHERE ref_announce_id = 62 AND latest_action = TRUE;
    @Query("SELECT m FROM Moderation m WHERE m.announceId = :id_announce AND m.latestAction = true")
    List<Moderation> searchPreviousModeration(@Param("id_announce") int idAnnonce);

    @Query("SELECT m FROM Moderation m WHERE m.latestAction = true")
    List<Moderation> findAllLatestActions();

    @Query("SELECT m FROM Moderation m WHERE m.latestAction = true")
    Page<Moderation> findAllLatestActions(Pageable pageable);
    
    List<Moderation> findByAnnounceIdOrderByModerationDateDesc(int announceId);

    Page<Moderation> findModerationByAnnounceIdOrderByModerationDateDesc(int announceId, Pageable pageable);
}