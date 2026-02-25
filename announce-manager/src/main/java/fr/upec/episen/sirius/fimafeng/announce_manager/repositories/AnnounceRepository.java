package fr.upec.episen.sirius.fimafeng.announce_manager.repositories;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
}
