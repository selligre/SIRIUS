package fr.upec.episen.sirius.fimafeng.notifications.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.upec.episen.sirius.fimafeng.notifications.dtos.CreateNotificationDTO;
import fr.upec.episen.sirius.fimafeng.notifications.dtos.NotificationDTO;
import fr.upec.episen.sirius.fimafeng.notifications.dtos.UnreadCountDTO;
import fr.upec.episen.sirius.fimafeng.notifications.services.NotificationService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger LOGGER = Logger.getLogger(NotificationController.class.getName());

    @Autowired
    private NotificationService notificationService;

    /**
     * Crée une nouvelle notification
     * @param createNotificationDTO Les données de la notification à créer
     * @return La notification créée avec un code HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) {
        try {
            LOGGER.info("Création d'une notification pour l'utilisateur: " + createNotificationDTO.getUserId());
            NotificationDTO createdNotification = notificationService.createNotification(createNotificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Enum invalide: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création de la notification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère toutes les notifications d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste de toutes les notifications de cet utilisateur
     */
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@RequestParam int userId) {
        try {
            LOGGER.info("Récupération des notifications pour l'utilisateur: " + userId);
            List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération des notifications: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère le nombre de notifications non-lues pour un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return Le nombre de notifications non-lues
     */
    @GetMapping("/unread-count")
    public ResponseEntity<UnreadCountDTO> getUnreadCount(@RequestParam int userId) {
        try {
            LOGGER.info("Récupération du nombre de notifications non-lues pour l'utilisateur: " + userId);
            long unreadCount = notificationService.getUnreadCountByUserId(userId);
            UnreadCountDTO dto = new UnreadCountDTO(userId, unreadCount);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération du nombre de notifications non-lues: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère toutes les notifications non-lues d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste des notifications non-lues
     */
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@RequestParam int userId) {
        try {
            LOGGER.info("Récupération des notifications non-lues pour l'utilisateur: " + userId);
            List<NotificationDTO> notifications = notificationService.getUnreadNotificationsByUserId(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la récupération des notifications non-lues: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Marque une notification comme lue
     * @param id L'ID de la notification
     * @return La notification marquée comme lue
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable long id) {
        try {
            LOGGER.info("Marquage de la notification comme lue: " + id);
            NotificationDTO notification = notificationService.markAsRead(id);
            
            if (notification != null) {
                return ResponseEntity.ok(notification);
            } else {
                LOGGER.warning("Notification non trouvée: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.severe("Erreur lors du marquage de la notification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Marque plusieurs notifications comme lues
     * @param notificationIds La liste des IDs des notifications à marquer
     * @return La liste des notifications marquées comme lues
     */
    @PutMapping("/read")
    public ResponseEntity<List<NotificationDTO>> markMultipleAsRead(@RequestBody List<Long> notificationIds) {
        try {
            LOGGER.info("Marquage de " + notificationIds.size() + " notifications comme lues");
            List<NotificationDTO> notifications = notificationService.markMultipleAsRead(notificationIds);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors du marquage des notifications: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
