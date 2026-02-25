package fr.upec.episen.sirius.fimafeng.announce_manager.controllers;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDTO;
import fr.upec.episen.sirius.fimafeng.announce_manager.services.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*")
public class AnnounceController {

    private static final Logger LOGGER = Logger.getLogger(AnnounceController.class.getName());

    @Autowired
    private AnnounceService announceService;

    /**
     * Crée une nouvelle annonce à partir du formulaire web
     * @param announceDTO Les données de l'annonce provenant du formulaire
     * @return L'annonce créée avec un code HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Announce> createAnnounce(@RequestBody AnnounceDTO announceDTO) {
        try {
            LOGGER.info("Received POST request to create announcement: " + announceDTO.getTitle());
            Announce createdAnnounce = announceService.createAnnounce(announceDTO);
            LOGGER.info("Announcement created successfully with ID: " + createdAnnounce.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnounce);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid enum value: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            LOGGER.severe("Error creating announcement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
