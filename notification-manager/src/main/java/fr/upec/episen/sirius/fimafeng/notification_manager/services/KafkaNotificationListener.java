package fr.upec.episen.sirius.fimafeng.notification_manager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.upec.episen.sirius.fimafeng.commons.models.NotificationEvent;
import fr.upec.episen.sirius.fimafeng.notification_manager.dtos.CreateNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaNotificationListener.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topicPattern = "(notifications-user-.*)", groupId = "${spring.kafka.consumer.group-id:notifications-service}")
    public void listen(@Payload String message,
                       @Header(name = "kafka_receivedTopic", required = false) String topic,
                       @Header(name = "kafka_receivedMessageKey", required = false) String key) {
        try {
            LOGGER.info("Message Kafka reçu sur topic={} key={}: {}", topic, key, message);

            // Some producers send a JSON string (i.e. a quoted JSON) as the value.
            // If so, first deserialize the outer string to get the real JSON payload.
            String payload = message;
            if (payload != null && payload.length() >= 2 && payload.charAt(0) == '"' && payload.charAt(payload.length() - 1) == '"') {
                // unescape the JSON string literal
                payload = objectMapper.readValue(payload, String.class);
            }

            NotificationEvent event = objectMapper.readValue(payload, NotificationEvent.class);
            LOGGER.info("NotificationEvent via mapper : "+ event);

            // Determine userId: prefer value in the event, then the message key, then the topic name
            int userId = event.getUserId();
            if (userId == 0) {
                // try key
                if (key != null) {
                    try {
                        userId = Integer.parseInt(key);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            if (userId == 0 && topic != null) {
                try {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("notifications-user-(\\d+)").matcher(topic);
                    if (m.find()) {
                        userId = Integer.parseInt(m.group(1));
                    }
                } catch (Exception ignored) {
                }
            }

            CreateNotificationDTO dto = new CreateNotificationDTO();
            dto.setUuid(event.getUuid());
            dto.setUserId(userId);
            dto.setAnnounceId(event.getAnnounceId());
            dto.setTitle(event.getMessage());

            notificationService.createNotification(dto);
            LOGGER.info("Notification persistée pour user={} announceId={}", userId, event.getAnnounceId());

        } catch (Exception e) {
            LOGGER.error("Erreur lors du traitement du message Kafka: ", e);
        }
    }
}
