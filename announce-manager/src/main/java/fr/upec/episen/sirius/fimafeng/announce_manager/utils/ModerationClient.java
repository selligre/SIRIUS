package fr.upec.episen.sirius.fimafeng.announce_manager.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.logging.Logger;

@Component
public class ModerationClient {

    private static final Logger LOGGER = Logger.getLogger(ModerationClient.class.getName());

    @Value("${moderation-service.url}")
    private String moderationServiceUrl;

    private final RestTemplate restTemplate;

    public ModerationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Demande la modération d'une annonce en envoyant son id au service de moderation
     * @param announceId id de l'annonce
     */
    public void requestModeration(int announceId) {
        try {
            String url = moderationServiceUrl + "/api/moderation/announce";

            String payload = String.format("{\"id\":%d}", announceId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            restTemplate.postForObject(url, entity, String.class);

            LOGGER.info("Requested moderation for announce " + announceId);
        } catch (Exception e) {
            LOGGER.warning("Erreur lors de l'appel au service de moderation: " + e.getMessage());
        }
    }
}
