package fr.upec.episen.sirius.fimafeng.notification_sender.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.upec.episen.sirius.fimafeng.commons.models.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationSenderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaNotificationSenderListener.class);

    @Autowired
    private NotificationSessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topicPattern = "notifications-user-.*", groupId = "${spring.kafka.consumer.group-id:notifications-sender}")
    public void listen(@Payload String message,
                       @Header(name = "kafka_receivedTopic", required = false) String topic,
                       @Header(name = "kafka_receivedMessageKey", required = false) String key) {
        try {
            LOGGER.info("Message Kafka reçu sur topic={} key={}: {}", topic, key, message);

            NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);

            // forward to connected websocket sessions for this user
            sessionService.sendNotificationToUser(event);

        } catch (Exception e) {
            LOGGER.error("Erreur lors du traitement du message Kafka: ", e);
        }
    }
}
