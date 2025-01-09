package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query(value = "SELECT * FROM location AS s ORDER BY s.name DESC LIMIT 1", nativeQuery = true)
    District findLastSampleByDate();

}