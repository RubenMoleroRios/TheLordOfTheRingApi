package com.ruben.lotr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ruben.lotr")
@EntityScan("com.ruben.lotr.core.character.infrastructure.hibernate.entities")
public class ThelordoftheringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThelordoftheringApiApplication.class, args);
	}

}
