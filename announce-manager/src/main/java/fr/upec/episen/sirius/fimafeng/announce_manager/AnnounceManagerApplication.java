package fr.upec.episen.sirius.fimafeng.announce_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng"
})
@EnableJpaRepositories(basePackages = {
    "fr.upec.episen.sirius.fimafeng"
})
@ComponentScan(basePackages = {
    "fr.upec.episen.sirius.fimafeng"
})
public class AnnounceManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnounceManagerApplication.class, args);
    }
}