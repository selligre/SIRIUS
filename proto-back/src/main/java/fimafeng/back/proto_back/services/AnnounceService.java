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

    public Announce save(Announce announce) {
        return announceRepository.save(announce);
    }

    public Announce findById(int idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        return optionalAnnounce.orElse(null);
    }

    public List<Announce> findAll() {
        return announceRepository.findAll();
    }

    public boolean update(Announce updatedAnnounce) {
        // Vérifiez si l'objet est null
        if (updatedAnnounce == null) {
            throw new IllegalArgumentException("The updated announce must not be null");
        }

        int id = updatedAnnounce.getId();

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
        announce.setAuthorId(updatedAnnounce.getAuthorId());
        announce.setRefDistrictId(updatedAnnounce.getRefDistrictId());

        // Sauvegardez les modifications
        announceRepository.saveAndFlush(announce);

        return true;
    }

    public boolean delete(int idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        if (optionalAnnounce.isPresent()) {
            optionalAnnounce.ifPresent(announce -> announceRepository.delete(announce));
            return true;
        }
        return false;
    }
}