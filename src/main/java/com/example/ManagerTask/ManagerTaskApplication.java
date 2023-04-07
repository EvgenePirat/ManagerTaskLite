package com.example.ManagerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ManagerTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerTaskApplication.class, args);
	}

}
