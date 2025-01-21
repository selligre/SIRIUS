package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.Announce;
import fimafeng.back.proto_back.models.enums.AnnounceStatus;
import fimafeng.back.proto_back.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    @Autowired
    private ModerationService moderationService;

    public Announce save(Announce announce) {
        // Save announce
        if(announce.getStatus() == AnnounceStatus.DRAFT) {
            announceRepository.save(announce);
        } else {
            announce.setStatus(AnnounceStatus.TO_ANALYSE);
            announceRepository.saveAndFlush(announce);
            moderationService.analyse(announce);
        }
        return announceRepository.save(announce);
    }



    public Announce findById(int idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        return optionalAnnounce.orElse(null);
    }

    public List<Announce> findAll() {
        return announceRepository.findAll();
    }

    public boolean update(Announce updatedAnnounce, boolean fromModeration) {
        // Check if object is null
        if (updatedAnnounce == null) {
            throw new IllegalArgumentException("The updated announce must not be null");
        }

        int id = updatedAnnounce.getId();

        // Check if announce exists
        Optional<Announce> optionalAnnounce = announceRepository.findById(id);
        if (optionalAnnounce.isEmpty()) {
            return false; // If it doesn't, then return false
        }

        // Update announce fields
        Announce announce = optionalAnnounce.get();
        announce.setPublicationDate(updatedAnnounce.getPublicationDate());
        announce.setType(updatedAnnounce.getType());
        announce.setTitle(updatedAnnounce.getTitle());
        announce.setDescription(updatedAnnounce.getDescription());
        announce.setDateTimeStart(updatedAnnounce.getDateTimeStart());
        announce.setDuration(updatedAnnounce.getDuration());
        announce.setDateTimeEnd(updatedAnnounce.getDateTimeEnd());
        announce.setIsRecurrent(updatedAnnounce.getIsRecurrent());
        announce.setAuthorId(updatedAnnounce.getAuthorId());

        // Save announce
        if(updatedAnnounce.getStatus() == AnnounceStatus.DRAFT || fromModeration) {
            announce.setStatus(updatedAnnounce.getStatus());
            announceRepository.save(announce);
        } else {
            announce.setStatus(AnnounceStatus.TO_ANALYSE);
            announceRepository.saveAndFlush(announce);
            moderationService.analyse(announce);
        }

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