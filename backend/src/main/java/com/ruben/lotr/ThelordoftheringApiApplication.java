package com.ruben.lotr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.ruben.lotr")
@EntityScan({
		"com.ruben.lotr.core.hero.infrastructure.hibernate.entities",
		"com.ruben.lotr.core.auth.infrastructure.persistence.entity"
})
@EnableJpaRepositories(basePackages = "com.ruben.lotr.core.auth.infrastructure.persistence")
public class ThelordoftheringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThelordoftheringApiApplication.class, args);
	}

}
