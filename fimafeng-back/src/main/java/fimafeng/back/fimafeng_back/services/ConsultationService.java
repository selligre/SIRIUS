package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.models.Consultation;
import fimafeng.back.fimafeng_back.repositories.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation save(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public Consultation findById(int idConsultation) {
        Optional<Consultation> optionalConsultation = consultationRepository.findById(idConsultation);
        return optionalConsultation.orElse(null);
    }

    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    public boolean update(Consultation updatedConsultation) {
        if (updatedConsultation == null) throw new IllegalArgumentException("consultation is null");
        int id = updatedConsultation.getId();

        Optional<Consultation> optionalConsultation = consultationRepository.findById(id);
        if (optionalConsultation.isEmpty()) return false;

        Consultation consultation = optionalConsultation.get();
        consultation.setRefUserId(updatedConsultation.getRefUserId());
        consultation.setRefAnnounceId(updatedConsultation.getRefAnnounceId());

        consultationRepository.saveAndFlush(consultation);
        return true;
    }

    public boolean delete(int idConsultation) {
        Optional<Consultation> optionalConsultation = consultationRepository.findById(idConsultation);
        if (optionalConsultation.isPresent()) {
            consultationRepository.deleteById(idConsultation);
            return true;
        }
        return false;
    }

}
