package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import fimafeng.back.fimafeng_back.repositories.ModerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ModerationService {


    Logger LOGGER = Logger.getLogger(ModerationService.class.getName());


    @Autowired
    private ModerationRepository moderationRepository;


    public Moderation findById(int idModeration) {
        Optional<Moderation> optionalModeration = moderationRepository.findById(idModeration);
        return optionalModeration.orElse(null);
    }

    public List<Moderation> findAll() {
        return moderationRepository.findAll();
    }

    public List<Moderation> findAllLatestAction() { return moderationRepository.findAllLatestActions(); }

    public Page<Moderation> findAllLatestAction(Pageable pageable) { return moderationRepository.findAllLatestActions(pageable); }

    public boolean update(Moderation updatedModeration) {
        if (updatedModeration == null) throw new IllegalArgumentException("moderation is null");
        int id = updatedModeration.getId();

        Optional<Moderation> optionalModeration = moderationRepository.findById(id);
        if (optionalModeration.isEmpty()) return false;

        Moderation moderation = optionalModeration.get();
        moderation.setLatestAction(false);
        LOGGER.info(moderation.toString());
        moderationRepository.save(moderation);

        Moderation newModeration = new Moderation();
        newModeration.setModeratorName("MODERATOR_NAME"); // TODO : mettre en place un systeme de connexion rapide
        newModeration.setModerationDate(new Date());
        newModeration.setAnnounceId(updatedModeration.getAnnounceId());
        newModeration.setAuthorId(updatedModeration.getAuthorId());
        newModeration.setReason(updatedModeration.getReason());
        newModeration.setDescription(updatedModeration.getDescription());
        newModeration.setAnnounceTitle(updatedModeration.getAnnounceTitle());
        newModeration.setAnnounceDescription(updatedModeration.getAnnounceDescription());
        newModeration.setAnnounceType(updatedModeration.getAnnounceType());
        newModeration.setAnnouncePublicationDate(updatedModeration.getAnnouncePublicationDate());
        newModeration.setLatestAction(true);

        LOGGER.info(newModeration.toString());
        moderationRepository.saveAndFlush(newModeration);
        return true;
    }

    public Moderation save(Moderation moderation) {
        LOGGER.info("Saving Moderation action");
        return moderationRepository.save(moderation);
    }

    public boolean delete(int idModeration) {
        Optional<Moderation> optionalModeration = moderationRepository.findById(idModeration);
        if (optionalModeration.isPresent()) {
            optionalModeration.ifPresent(moderation -> moderationRepository.delete(moderation));
            return true;
        }
        return false;
    }

    public void updatePreviousModeration(int idAnnounce) {
        List<Moderation> moderations = moderationRepository.searchPreviousModeration(idAnnounce);
        for (Moderation moderation : moderations) {
            moderation.setLatestAction(false);
        }
        moderationRepository.saveAll(moderations);
    }

    public List<Moderation> findModerationByAnnounceId(int idAnnounce) {
        return moderationRepository.findByAnnounceIdOrderByModerationDateDesc(idAnnounce);
    }

    public Page<Moderation> findModerationByAnnounceId(int idAnnounce, Pageable pageable) {
        return moderationRepository.findModerationByAnnounceIdOrderByModerationDateDesc(idAnnounce, pageable);
    }

    public Page<Moderation> findModerationByAnnounceStatus(AnnounceStatus status, Pageable pageable) {
        return moderationRepository.findLatestActionsForStatus(pageable, status);
    }




}
