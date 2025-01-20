package fimafeng.back.proto_back;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
@EnableJpaRepositories(basePackages = "fimafeng.back.proto_back.repositories")
public class SpringConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("")
                .allowedOrigins("")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(86400);
    }
}