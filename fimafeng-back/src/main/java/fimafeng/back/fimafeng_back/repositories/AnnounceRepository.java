package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Announce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
@Query(value="SELECT a.* FROM announce a " +
        "LEFT JOIN announce_tag at ON a.id = at.ref_announce_id " +
        "WHERE (:keyword IS NULL OR a.title LIKE %:keyword% OR a.description LIKE %:keyword%) " +
        "AND (:refLocationId IS NULL OR a.ref_location_id = :refLocationId) " +
        "AND (:tagCount = 0 OR at.ref_tag_id IN :tagIds) " +
        "GROUP BY a.id " +
        "HAVING (:tagCount = 0 OR COUNT(DISTINCT at.ref_tag_id) = :tagCount)", nativeQuery = true)
Page<Announce> searchByKeyword(
        @Param("keyword") String keyword,
        @Param("refLocationId") Integer refLocationId,
        @Param("tagIds") List<Long> tagIds,
        @Param("tagCount") Integer tagCount,
        Pageable pageable);
}
