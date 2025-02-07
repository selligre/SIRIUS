package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value="SELECT a.ref_location_id AS location, COUNT(a) " +
            "FROM announce a " +
            "GROUP BY a.ref_location_id " +
            "HAVING COUNT(a) > 0", nativeQuery = true)
    List<LocationCountProjection> countAnnouncesByLocation();

    @Query(value="SELECT d.id AS district, COUNT(a.id) " +
            "FROM district d " +
            "LEFT JOIN location l ON d.id = l.ref_district " +
            "LEFT JOIN announce a ON l.location_id = a.ref_location_id " +
            "GROUP BY d.id " +
            "ORDER BY d.id", nativeQuery = true)
    List<DistrictAnnounceCountProjection> countOfAnnounceByDistrict();
}