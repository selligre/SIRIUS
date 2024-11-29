package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.Announce;
import fimafeng.back.proto_back.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    public Announce saveAnnounce(Announce announce) {
        return announceRepository.save(announce);
    }

    public Announce findByIdAnnounce(Long idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        return optionalAnnounce.orElse(null);
    }

    public List<Announce> findAllAnnounce(){
        return announceRepository.findAll();
    }

    public boolean updateAnnounce(Announce updatedAnnounce) {
        // Vérifiez si l'objet ou son ID est null
        if (updatedAnnounce == null || updatedAnnounce.getIdAnnounce() == null) {
            throw new IllegalArgumentException("The updated announce or its ID must not be null");
        }
    
        Long id = updatedAnnounce.getIdAnnounce();
    
        // Vérifiez si l'annonce existe
        Optional<Announce> optionalAnnounce = announceRepository.findById(id);
        if (optionalAnnounce.isEmpty()) {
            return false; // Si l'annonce n'existe pas, retournez false
        }
    
        // Mise à jour des champs de l'entité existante
        Announce announce = optionalAnnounce.get();
        announce.setPublicationDate(updatedAnnounce.getPublicationDate());
        announce.setStatus(updatedAnnounce.getStatus());
        announce.setType(updatedAnnounce.getType());
        announce.setTitle(updatedAnnounce.getTitle());
        announce.setDescription(updatedAnnounce.getDescription());
        announce.setDateTimeStart(updatedAnnounce.getDateTimeStart());
        announce.setDuration(updatedAnnounce.getDuration());
        announce.setDateTimeEnd(updatedAnnounce.getDateTimeEnd());
        announce.setIsRecurrent(updatedAnnounce.getIsRecurrent());
    
        // Sauvegardez les modifications
        announceRepository.saveAndFlush(announce);
    
        return true;
    }

    public boolean deleteAnnounce(Long idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        if (optionalAnnounce.isPresent()) {
            optionalAnnounce.ifPresent(announce -> announceRepository.delete(announce));
            return true;
        }
        return false;
    }
}