package fr.upec.episen.sirius.fimafeng.announce_manager.utils;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.upec.episen.sirius.fimafeng.commons.models.NotificationEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
    private static final String TOPIC = "notifications-topic";

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationEvent event) {
        logger.info("Envoi d'une notification à Kafka: {}", event.getId());
        
        // Envoi asynchrone
        this.kafkaTemplate.send(TOPIC, Integer.toString(event.getUserId()), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Message envoyé avec succès à l'offset {}", result.getRecordMetadata().offset());
                } else {
                    logger.error("Erreur lors de l'envoi du message", ex);
                }
            });
    }
}