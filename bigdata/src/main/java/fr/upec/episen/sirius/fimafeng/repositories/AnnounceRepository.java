package fr.upec.episen.sirius.fimafeng.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.upec.episen.sirius.fimafeng.models.Announce;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {
}