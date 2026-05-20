package fr.upec.episen.sirius.fimafeng.moderation.controllers;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.commons.dtos.AnnounceDAO;
import fr.upec.episen.sirius.fimafeng.moderation.services.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/moderation")
public class ModerationController {

    private static final Logger LOGGER = Logger.getLogger(ModerationController.class.getName());

    @Autowired
    private AnnounceService announceService;

    /**
     * Endpoint appelé par l'Announce Manager pour demander la modération d'une annonce.
     * Expose POST /api/moderation/announce
     */
    @PostMapping("/announce")
    public ResponseEntity<Announce> moderateAnnounce(@RequestBody AnnounceDAO announceDAO) {
        try {
            LOGGER.info("Received announce for moderation: " + announceDAO.getId());
            Announce updated = announceService.moderateAnnounce(announceDAO);
            LOGGER.info("Moderation completed for announce: " + updated.getId() + " status=" + updated.getStatus());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Announcement not found: " + announceDAO.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.severe("Error during moderation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
