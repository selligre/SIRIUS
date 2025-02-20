package fimafeng.back.fimafeng_back.repositories;

import fimafeng.back.fimafeng_back.models.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
}
