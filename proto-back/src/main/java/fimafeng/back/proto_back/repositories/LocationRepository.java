package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value="SELECT d.id AS ref_district, COUNT(l.ref_district) AS count " +
            "FROM district d " +
            "LEFT JOIN location l ON d.id = l.ref_district " +
            "GROUP BY d.id " +
            "ORDER BY d.id", nativeQuery = true)
    List<LocationCountProjection> countOfLocation();

}