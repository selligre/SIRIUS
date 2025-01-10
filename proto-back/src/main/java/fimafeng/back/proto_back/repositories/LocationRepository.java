package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value=" SELECT ref_district,COUNT(ref_district) AS count FROM location GROUP BY ref_district ORDER BY ref_district", nativeQuery = true)
    List<LocationCountProjection> countOfLocation();

}