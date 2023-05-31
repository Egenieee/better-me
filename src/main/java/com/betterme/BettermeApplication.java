package com.betterme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BettermeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettermeApplication.class, args);
	}

}
