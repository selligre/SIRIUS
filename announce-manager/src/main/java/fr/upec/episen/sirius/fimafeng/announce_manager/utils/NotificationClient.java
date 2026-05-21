package fr.upec.episen.sirius.fimafeng.announce_manager.utils;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * Utilitaire pour faire des requêtes HTTP vers le service de notifications
 */
@Component
public class NotificationClient {

    private static final Logger LOGGER = Logger.getLogger(NotificationClient.class.getName());

    private final KafkaTemplate<String, String> kafkaTemplate;

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
    public void sendNotification(int userId, int announceId, String status, String message) {
        try {
            String topic = String.format("notifications-user-%d", userId);

            String createdAt = Instant.now().toString();

            // Créer la payload JSON contenant announceId, status et datetime de création
            String payload = String.format(
                "{\"announceId\":%d,\"status\":\"%s\",\"createdAt\":\"%s\",\"message\":\"%s\"}",
                announceId, status, createdAt, escapeJson(message)
            );

            kafkaTemplate.send(topic, Integer.toString(userId), payload);

            LOGGER.info("Notification Kafka produite sur " + topic + " pour l'annonce " + announceId);
        } catch (Exception e) {
            LOGGER.warning("Erreur lors de l'envoi de la notification Kafka: " + e.getMessage());
        }
    }

    // Petit utilitaire d'échappement basique pour éviter les quotes non-échappées
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
