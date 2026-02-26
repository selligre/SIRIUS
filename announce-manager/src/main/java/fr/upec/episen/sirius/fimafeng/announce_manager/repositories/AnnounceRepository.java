package fr.upec.episen.sirius.fimafeng.announce_manager.repositories;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
    /**
     * Récupère toutes les annonces d'un utilisateur spécifique
     * @param authorId L'ID de l'auteur
     * @return Une liste des annonces de cet utilisateur
     */
    List<Announce> findByAuthorId(int authorId);

    /**
     * Récupère toutes les annonces enrichies avec les informations de l'auteur (username)
     * @return Une liste de tous les resultats avec join sur la table app_user
     */
    @Query(value = "SELECT a.id, a.title, a.description, a.date_time_start, a.date_time_end, " +
           "a.publication_date, a.status, a.type, a.author_id, u.username, a.duration " +
           "FROM announce a " +
           "LEFT JOIN app_user u ON a.author_id = u.id " +
           "ORDER BY a.publication_date DESC", nativeQuery = true)
    List<Map<String, Object>> findAllWithAuthorInfo();

    /**
     * Récupère une annonce enrichie par son ID avec les informations de l'auteur
     * @param id L'ID de l'annonce
     * @return Un Optional contenant les données si trouvées
     */
    @Query(value = "SELECT a.id, a.title, a.description, a.date_time_start, a.date_time_end, " +
           "a.publication_date, a.status, a.type, a.author_id, u.username, a.duration " +
           "FROM announce a " +
           "LEFT JOIN app_user u ON a.author_id = u.id " +
           "WHERE a.id = :id", nativeQuery = true)
    Optional<Map<String, Object>> findByIdWithAuthorInfo(@Param("id") int id);

    /**
     * Récupère toutes les annonces d'un utilisateur enrichies avec les informations de l'auteur
     * @param authorId L'ID de l'auteur
     * @return Une liste des resultats avec les infos d'auteur
     */
    @Query(value = "SELECT a.id, a.title, a.description, a.date_time_start, a.date_time_end, " +
           "a.publication_date, a.status, a.type, a.author_id, u.username, a.duration " +
           "FROM announce a " +
           "LEFT JOIN app_user u ON a.author_id = u.id " +
           "WHERE a.author_id = :authorId " +
           "ORDER BY a.publication_date DESC", nativeQuery = true)
    List<Map<String, Object>> findByAuthorIdWithAuthorInfo(@Param("authorId") int authorId);
}
