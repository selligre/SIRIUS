package fr.upec.episen.sirius.fimafeng.notifications.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.upec.episen.sirius.fimafeng.commons.models.Notification;
import fr.upec.episen.sirius.fimafeng.commons.models.enums.NotificationTitle;
import fr.upec.episen.sirius.fimafeng.notifications.dtos.CreateNotificationDTO;
import fr.upec.episen.sirius.fimafeng.notifications.dtos.NotificationDTO;
import fr.upec.episen.sirius.fimafeng.notifications.repositories.NotificationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Crée une nouvelle notification
     * @param createNotificationDTO Les données de la notification à créer
     * @return La notification créée
     */
    public NotificationDTO createNotification(CreateNotificationDTO createNotificationDTO) {
        Notification notification = new Notification();
        notification.setUserId(createNotificationDTO.getUserId());
        notification.setAnnounceId(createNotificationDTO.getAnnounceId());
        notification.setTitle(NotificationTitle.getNotificationTitle(createNotificationDTO.getTitle()));
        notification.setMessage(createNotificationDTO.getMessage());
        notification.setCreationDate(new Date());
        notification.setHasBeenRed(false); // Les notifications sont initiées à l'état non-lue

        Notification savedNotification = notificationRepository.save(notification);
        LOGGER.info("Notification créée avec succès: ID=" + savedNotification.getId());

        return convertToDTO(savedNotification);
    }

    /**
     * Récupère toutes les notifications d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste de toutes les notifications de cet utilisateur
     */
    public List<NotificationDTO> getNotificationsByUserId(int userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les notifications non-lues d'un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return La liste des notifications non-lues
     */
    public List<NotificationDTO> getUnreadNotificationsByUserId(int userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndHasBeenRedFalse(userId);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Compte le nombre de notifications non-lues pour un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return Le nombre de notifications non-lues
     */
    public long getUnreadCountByUserId(int userId) {
        long count = notificationRepository.countByUserIdAndHasBeenRedFalse(userId);
        LOGGER.info("Nombre de notifications non-lues pour l'utilisateur " + userId + ": " + count);
        return count;
    }

    /**
     * Marque une notification comme lue
     * @param notificationId L'ID de la notification
     * @return La notification marquée comme lue
     */
    public NotificationDTO markAsRead(long notificationId) {
        Optional<Notification> optional = notificationRepository.findById(notificationId);

        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setHasBeenRed(true);
            Notification updatedNotification = notificationRepository.save(notification);
            LOGGER.info("Notification marquée comme lue: ID=" + notificationId);
            return convertToDTO(updatedNotification);
        } else {
            LOGGER.warning("Notification non trouvée: ID=" + notificationId);
            return null;
        }
    }

    /**
     * Marque plusieurs notifications comme lues
     * @param notificationIds La liste des IDs des notifications à marquer
     * @return La liste des notifications marquées comme lues
     */
    public List<NotificationDTO> markMultipleAsRead(List<Long> notificationIds) {
        List<Notification> notifications = notificationRepository.findAllById(notificationIds);

        for (Notification notification : notifications) {
            notification.setHasBeenRed(true);
        }

        List<Notification> updatedNotifications = notificationRepository.saveAll(notifications);
        LOGGER.info("Nombre de notifications marquées comme lues: " + updatedNotifications.size());

        return updatedNotifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une entité Notification en DTO
     * @param notification L'entité Notification
     * @return Le DTO correspondant
     */
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setAnnounceId(notification.getAnnounceId());
        dto.setCreationDate(notification.getCreationDate());
        dto.setHasBeenRed(notification.isHasBeenRed());
        dto.setTitle(notification.getTitle().getValue());
        dto.setMessage(notification.getMessage());
        return dto;
    }
}
