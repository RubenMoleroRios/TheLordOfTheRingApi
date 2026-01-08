package com.ruben.lotr.thelordofthering_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ruben.lotr")
public class ThelordoftheringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThelordoftheringApiApplication.class, args);
	}

}
