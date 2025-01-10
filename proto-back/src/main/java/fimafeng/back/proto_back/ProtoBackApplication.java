package fimafeng.back.proto_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class ProtoBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtoBackApplication.class, args);
	}

}
