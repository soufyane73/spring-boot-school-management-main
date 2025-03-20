package com.ikitama.management_of_schools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ManagementOfSchoolsApplication {

	public static void main(String[] args) {

		// Chemin vers le répertoire "logs"
		String logsDirectoryPath = "logs";

		// Créer le répertoire s'il n'existe pas déjà
		File logsDirectory = new File(logsDirectoryPath);
		if (!logsDirectory.exists()) {
			if (logsDirectory.mkdirs()) {
				System.out.println("Répertoire 'logs' créé avec succès.");
			}
		}
		SpringApplication.run(ManagementOfSchoolsApplication.class, args);
	}

}
