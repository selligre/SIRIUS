package fr.upec.episen.sirius.fimafeng.announce_manager.controllers;

import fr.upec.episen.sirius.fimafeng.commons.models.Announce;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDTO;
import fr.upec.episen.sirius.fimafeng.announce_manager.dtos.AnnounceDAO;
import fr.upec.episen.sirius.fimafeng.announce_manager.services.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/announcements")
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

    /**
     * Récupère toutes les annonces d'un utilisateur spécifique
     * @param userId L'ID de l'utilisateur
     * @return Une liste des annonces de cet utilisateur
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Announce>> getAnnouncesByUser(@PathVariable int userId) {
        try {
            LOGGER.info("Received GET request for announcements of user: " + userId);
            List<Announce> announcements = announceService.getAnnouncesByAuthorId(userId);
            LOGGER.info("Retrieved " + announcements.size() + " announcements for user: " + userId);
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            LOGGER.severe("Error retrieving announcements: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère une annonce par son ID
     * @param id L'ID de l'annonce
     * @return L'annonce si elle existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Announce> getAnnounceById(@PathVariable int id) {
        try {
            LOGGER.info("Received GET request for announcement: " + id);
            Optional<Announce> announce = announceService.getAnnounceById(id);
            
            if (announce.isPresent()) {
                return ResponseEntity.ok(announce.get());
            } else {
                LOGGER.warning("Announcement not found: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.severe("Error retrieving announcement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Met à jour une annonce existante
     * @param id L'ID de l'annonce
     * @param announceDTO Les nouvelles données
     * @param userId L'ID de l'utilisateur effectuant la modification
     * @return L'annonce mise à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Announce> updateAnnounce(
            @PathVariable int id,
            @RequestBody AnnounceDTO announceDTO,
            @RequestParam int userId) {
        try {
            LOGGER.info("Received PUT request to update announcement: " + id);
            Announce updatedAnnounce = announceService.updateAnnounce(id, announceDTO, userId);
            LOGGER.info("Announcement updated successfully: " + id);
            return ResponseEntity.ok(updatedAnnounce);
        } catch (IllegalAccessException e) {
            LOGGER.warning("Unauthorized update attempt: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.severe("Error updating announcement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprime une annonce
     * @param id L'ID de l'annonce
     * @param userId L'ID de l'utilisateur effectuant la suppression
     * @return Un code HTTP 204 (No Content) si succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnounce(
            @PathVariable int id,
            @RequestParam int userId) {
        try {
            LOGGER.info("Received DELETE request for announcement: " + id);
            announceService.deleteAnnounce(id, userId);
            LOGGER.info("Announcement deleted successfully: " + id);
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {
            LOGGER.warning("Unauthorized delete attempt: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Announcement not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.severe("Error deleting announcement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère toutes les annonces enrichies avec les informations de l'auteur (username, type et status labels)
     * @return Une liste de tous les AnnounceDAO pour affichage au frontend
     */
    @GetMapping("/enriched/all")
    public ResponseEntity<List<AnnounceDAO>> getAllAnnouncesEnriched() {
        try {
            LOGGER.info("Received GET request for all announcements enriched with author info");
            List<AnnounceDAO> announcements = announceService.getAllAnnouncesWithAuthorInfo();
            LOGGER.info("Retrieved " + announcements.size() + " enriched announcements");
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            LOGGER.severe("Error retrieving enriched announcements: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère une annonce enrichie par son ID avec les informations de l'auteur
     * @param id L'ID de l'annonce
     * @return L'annonce enrichie si elle existe
     */
    @GetMapping("/enriched/{id}")
    public ResponseEntity<AnnounceDAO> getAnnounceByIdEnriched(@PathVariable int id) {
        try {
            LOGGER.info("Received GET request for enriched announcement: " + id);
            Optional<AnnounceDAO> announce = announceService.getAnnounceByIdWithAuthorInfo(id);
            
            if (announce.isPresent()) {
                return ResponseEntity.ok(announce.get());
            } else {
                LOGGER.warning("Announcement not found: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.severe("Error retrieving enriched announcement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère toutes les annonces enrichies d'un utilisateur spécifique
     * @param userId L'ID de l'utilisateur
     * @return Une liste des annonces enrichies de cet utilisateur
     */
    @GetMapping("/enriched/user/{userId}")
    public ResponseEntity<List<AnnounceDAO>> getAnnouncesByUserEnriched(@PathVariable int userId) {
        try {
            LOGGER.info("Received GET request for enriched announcements of user: " + userId);
            List<AnnounceDAO> announcements = announceService.getAnnouncesByAuthorIdWithAuthorInfo(userId);
            LOGGER.info("Retrieved " + announcements.size() + " enriched announcements for user: " + userId);
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            LOGGER.severe("Error retrieving enriched announcements: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

