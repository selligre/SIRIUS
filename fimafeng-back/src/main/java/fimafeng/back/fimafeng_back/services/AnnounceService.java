package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationImplementation;
import fimafeng.back.fimafeng_back.models.Announce;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    @Autowired
    private ModerationImplementation moderationImplementation;

    public Announce save(Announce announce) {
        // Save announce
        if(announce.getStatus() == AnnounceStatus.DRAFT) {
            announceRepository.save(announce);
        } else {
            announce.setStatus(AnnounceStatus.TO_ANALYSE);
            announceRepository.saveAndFlush(announce);
            moderationImplementation.analyse(announce);
        }
        return announceRepository.save(announce);
    }

    public Page<Announce> searchAnnounces(String keyword, Integer refLocationId, Pageable pageable) {
        return announceRepository.searchByKeyword(keyword, refLocationId, pageable);
    }

    public Announce findById(int idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        return optionalAnnounce.orElse(null);
    }

    public List<Announce> findAll() {
        return announceRepository.findAll();
    }

    public boolean update(Announce updatedAnnounce, boolean fromModeration) {
        if (updatedAnnounce == null) {
            throw new IllegalArgumentException("The updated announce must not be null");
        }

        int id = updatedAnnounce.getId();

        Optional<Announce> optionalAnnounce = announceRepository.findById(id);
        if (optionalAnnounce.isEmpty()) {
            return false;
        }

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
        announce.setRefLocationId(updatedAnnounce.getRefLocationId());
        announce.setRefDistrictId(updatedAnnounce.getRefDistrictId());

        // Save announce
        if(updatedAnnounce.getStatus() == AnnounceStatus.DRAFT || fromModeration) {
            announce.setStatus(updatedAnnounce.getStatus());
            announceRepository.save(announce);
        } else {
            announce.setStatus(AnnounceStatus.TO_ANALYSE);
            announceRepository.saveAndFlush(announce);
            moderationImplementation.analyse(announce);
        }

        return true;
    }

    public boolean delete(int idAnnounce) {
        Optional<Announce> optionalAnnounce = announceRepository.findById(idAnnounce);
        if (optionalAnnounce.isPresent()) {
            Announce announce = optionalAnnounce.get();
            // TODO : récuperer les annonces_tags, puis les supprimer avant de supprimer l'annonce

            announceRepository.delete(announce);
            return true;
        }
        return false;
    }
}