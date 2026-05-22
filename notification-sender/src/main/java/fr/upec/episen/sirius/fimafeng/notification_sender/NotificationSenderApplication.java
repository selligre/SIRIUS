package fr.upec.episen.sirius.fimafeng.notification_sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng.commons.models",
    "fr.upec.episen.sirius.fimafeng.notification_sender"
})
@ComponentScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng.notification_sender",
    "fr.upec.episen.sirius.fimafeng.commons"
})
public class NotificationSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationSenderApplication.class, args);
    }
}
