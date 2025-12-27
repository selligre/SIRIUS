package fr.upec.episen.sirius.fimafeng.search.repository;

import fr.upec.episen.sirius.fimafeng.search.model.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {
    // Spring Data crée la requête SQL automatiquement grâce au nom de la méthode
    List<Announce> findByTitreContainingIgnoreCase(String keyword);
}