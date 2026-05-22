package fr.upec.episen.sirius.fimafeng.announce_manager.utils;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.Date;

import fr.upec.episen.sirius.fimafeng.announce_manager.repositories.NotificationRepository;
import fr.upec.episen.sirius.fimafeng.commons.models.Notification;

/**
 * Utilitaire pour faire des requêtes HTTP vers le service de notifications
 */
@Component
public class NotificationClient {

    private static final Logger LOGGER = Logger.getLogger(NotificationClient.class.getName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envoie une notification au service de notifications
     * @param userId L'ID de l'utilisateur
     * @param announceId L'ID de l'annonce
     * @param title Le titre de la notification
     * @param message Le message de la notification
     */
    @Async
    public void sendNotification(int userId, int announceId, String message) {
        String topic = String.format("notifications-user-%d", userId);

        Date createdAt = Date.from(Instant.now());

        UUID uuid = UUID.randomUUID();

        // Créer la payload JSON contenant announceId, message et datetime de création
        String payload = String.format(
            "{\"uuid\":%s,\"announceId\":%d,\"createdAt\":\"%s\",\"message\":\"%s\"}",
            uuid.toString(), announceId, createdAt.toString(), escapeJson(message)
        );

        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, Integer.toString(userId), payload);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    // Cas de SUCCÈS (onSuccess)
                    LOGGER.info("Notification Kafka produite sur " + topic + 
                                " pour l'annonce " + announceId + 
                                " à l'offset " + result.getRecordMetadata().offset());
                } else {
                    // Cas d'ERREUR (onFailure)
                    LOGGER.warning("Erreur lors de l'envoi de la notification Kafka: " + ex.getMessage());
                    fallbackCreateNotification(uuid, userId, announceId, createdAt, message);
                }
            });

        } catch (Exception e) {
            LOGGER.warning("Exception lors de l'envoi Kafka (envoi asynchrone) : " + e.getMessage());
            fallbackCreateNotification(uuid, userId, announceId, createdAt, message);
        }
    }

    private void fallbackCreateNotification(UUID uuid, int userId, int announceId, Date createdAt, String title) {
        try {
            if (notificationServiceUrl == null || notificationServiceUrl.isEmpty()) {
                LOGGER.warning("notificationServiceUrl non configuré, impossible d'utiliser le fallback HTTP");
                return;
            }
            Notification notif = new Notification();
            notif.setUuid(uuid);
            notif.setUserId(userId);
            notif.setAnnounceId(announceId);
            notif.setCreationDate(createdAt);
            notif.setHasBeenRed(false);

            notificationRepository.save(notif);

            LOGGER.info("Fallback : notification créée via repository pour l'utilisateur " + userId);
        } catch (Exception ex) {
            LOGGER.warning("Probleme lié au repository : " + ex.getMessage());
        }
    }

    // Petit utilitaire d'échappement basique pour éviter les quotes non-échappées
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
