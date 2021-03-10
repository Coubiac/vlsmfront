package com.begr.vlsmfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VlsmfrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(VlsmfrontApplication.class, args);
	}

}
