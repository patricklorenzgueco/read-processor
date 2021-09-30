package com.energy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Nem12readerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Nem12readerApplication.class, args);
	}

}
