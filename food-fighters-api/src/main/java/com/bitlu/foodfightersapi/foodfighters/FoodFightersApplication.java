package com.bitlu.foodfightersapi.foodfighters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class FoodFightersApplication {

	private static final Logger logger = LoggerFactory.getLogger(FoodFightersApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(FoodFightersApplication.class, args);
		logger.info("App running f");
	}

}
