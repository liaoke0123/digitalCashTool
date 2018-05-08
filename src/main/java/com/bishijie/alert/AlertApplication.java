package com.bishijie.alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@EnableScheduling
@SpringBootApplication
@Controller
public class AlertApplication {



	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}



}
