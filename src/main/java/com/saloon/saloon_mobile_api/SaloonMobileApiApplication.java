package com.saloon.saloon_mobile_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaloonMobileApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaloonMobileApiApplication.class, args);
	}

}
