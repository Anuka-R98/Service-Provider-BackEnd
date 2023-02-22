package com.ServiceProvider.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApplication.class, args);
		System.out.println("<------------------------->");
		System.out.println(" >> This App is running << ");
		System.out.println("<------------------------->");

	}
}
