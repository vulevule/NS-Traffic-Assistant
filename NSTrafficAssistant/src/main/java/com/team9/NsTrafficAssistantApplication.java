package com.team9;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.team9.service.UserService;

@SpringBootApplication
public class NsTrafficAssistantApplication {
	
	

	public static void main(String[] args) {
		//userSer.init();
		SpringApplication.run(NsTrafficAssistantApplication.class, args);
		
	}

	
}
