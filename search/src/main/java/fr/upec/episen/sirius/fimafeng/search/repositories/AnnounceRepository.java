package fr.upec.episen.sirius.fimafeng.search.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;

import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {

    List<Announce> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT a FROM Announce a WHERE a.title LIKE CONCAT('%', :keyword, '%') OR a.description LIKE CONCAT('%', :keyword, '%')")
    Page<Announce> searchByKeyword(String keyword, Pageable pageable);
}