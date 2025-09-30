package com.invgestorback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.invgestorback.controller" , "com.invgestorback.service", "com.invgestorback.repository", "com.invgestorback.config"})
public class InvgestorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvgestorApplication.class, args);
	}

}
