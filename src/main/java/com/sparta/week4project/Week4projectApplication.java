package com.sparta.week4project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.annotation.AuthenticationPrincipal;



@EnableJpaAuditing
@SpringBootApplication
public class Week4projectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Week4projectApplication.class, args);
	}

}
