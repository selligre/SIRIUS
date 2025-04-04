package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.AnnounceTag;
import fimafeng.back.fimafeng_back.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnounceTagRepository extends JpaRepository<AnnounceTag, Integer> {
    @Query(value = "SELECT tag.name, COUNT(tag.id) " +
            "FROM tag " +
            "JOIN announce_tag at ON tag.id = at.ref_tag_id " +
            "JOIN announce a ON at.ref_announce_id = a.id " +
            "WHERE ref_district_id = :districtId " +
            "GROUP BY tag.id " +
            "HAVING COUNT(tag.id) > 0", nativeQuery = true)
    List<TagCountProjection> countTagsByDistrict(@Param("districtId") int districtId);

    @Query(value = "SELECT t FROM Tag t " +
            "JOIN AnnounceTag at ON t.id = at.refTagId " +
            "WHERE at.refAnnounceId = :announceId")
    List<Tag> findTagsByAnnounceId(@Param("announceId") Integer announceId);
}
