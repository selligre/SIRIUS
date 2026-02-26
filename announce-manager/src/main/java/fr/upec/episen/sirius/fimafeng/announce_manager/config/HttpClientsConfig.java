package fr.upec.episen.sirius.fimafeng.announce_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration pour les clients HTTP
 */
@Configuration
public class HttpClientsConfig {

    /**
     * Crée un bean RestTemplate pour les appels HTTP
     * @return Le RestTemplate configuré
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
