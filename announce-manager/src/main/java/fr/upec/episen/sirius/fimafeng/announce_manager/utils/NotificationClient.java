package fr.upec.episen.sirius.fimafeng.announce_manager.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.logging.Logger;

/**
 * Utilitaire pour faire des requêtes HTTP vers le service de notifications
 */
@Component
public class NotificationClient {

    private static final Logger LOGGER = Logger.getLogger(NotificationClient.class.getName());

    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    private final RestTemplate restTemplate;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Envoie une requête de création de notification au service de notifications
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @param title Le titre de la notification
     * @param message Le message de la notification
     */
    public void notifyAnnounceCreated(int userId, int announceId, String title, String message) {
        sendNotification(userId, announceId, title, message);
    }

    /**
     * Envoie une requête de notification de modification d'annonce
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @param title Le titre de la notification
     * @param message Le message de la notification
     */
    public void notifyAnnounceUpdated(int userId, int announceId, String title, String message) {
        sendNotification(userId, announceId, title, message);
    }

    /**
     * Envoie une requête de notification de suppression d'annonce
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @param title Le titre de la notification
     * @param message Le message de la notification
     */
    public void notifyAnnounceDeleted(int userId, int announceId, String title, String message) {
        sendNotification(userId, announceId, title, message);
    }

    /**
     * Envoie une notification au service de notifications
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @param title Le titre de la notification
     * @param message Le message de la notification
     */
    private void sendNotification(int userId, int announceId, String title, String message) {
        try {
            String url = notificationServiceUrl + "/api/notifications";

            // Créer la payload
            String payload = String.format(
                "{\"userId\":%d,\"announceId\":%d,\"title\":\"%s\",\"message\":\"%s\"}",
                userId, announceId, title, message
            );

            // Créer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer l'entité HTTP
            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            // Envoyer la requête
            restTemplate.postForObject(url, entity, String.class);

            LOGGER.info("Notification créée avec succès pour l'utilisateur " + userId + " et l'annonce " + announceId);
        } catch (Exception e) {
            LOGGER.warning("Erreur lors de l'envoi de la notification: " + e.getMessage());
            // On log l'erreur mais on ne lève pas d'exception pour ne pas bloquer la création/modification/suppression de l'annonce
        }
    }
}
