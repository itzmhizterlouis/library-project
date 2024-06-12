package com.hslcreator.LibraryAPI;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class LibraryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
