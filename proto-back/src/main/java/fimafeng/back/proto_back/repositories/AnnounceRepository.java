package fimafeng.back.proto_back.repositories;

import fimafeng.back.proto_back.models.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {
    @Query(value="SELECT * FROM Sample AS s ORDER BY s.date_sample DESC LIMIT 1", nativeQuery = true)
    Announce findLastSampleByDate();

}