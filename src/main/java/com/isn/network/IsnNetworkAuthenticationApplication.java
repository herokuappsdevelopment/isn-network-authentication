package com.isn.network;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.isn.network.storage.StorageProperties;
import com.isn.network.storage.StorageService;

/**
 * @author tcts-india
 *
 */
@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class IsnNetworkAuthenticationApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(IsnNetworkAuthenticationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
