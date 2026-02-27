package fr.upec.episen.sirius.fimafeng.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
    // "fr.upec.episen.sirius.fimafeng.commons.models"
    "fr.upec.episen.sirius.fimafeng"
})
@EnableJpaRepositories(basePackages = {
    // "fr.upec.episen.sirius.fimafeng.search.repositories",
    // "fr.upec.episen.sirius.fimafeng.repositories"
    "fr.upec.episen.sirius.fimafeng"
})
@ComponentScan(basePackages = {
    // "fr.upec.episen.sirius.fimafeng.search",
    // "fr.upec.episen.sirius.fimafeng.commons"
    "fr.upec.episen.sirius.fimafeng"
})
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}