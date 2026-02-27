package fr.upec.episen.sirius.fimafeng.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng.commons.models",
    "fr.upec.episen.sirius.fimafeng.notifications"
})
@EnableJpaRepositories(basePackages = {
    "fr.upec.episen.sirius.fimafeng.notifications.repositories"
})
@ComponentScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng.notifications",
    "fr.upec.episen.sirius.fimafeng.commons"
})
public class NotificationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplication.class, args);
    }
}
