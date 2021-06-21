/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Main Application class
 */
@EnableFeignClients
@EnableOAuth2Client
@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {

	/**
	 * Main method
	 *
	 * @param args args arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
