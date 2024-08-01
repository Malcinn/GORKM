package com.gorkm.usersservice;

import com.gorkm.usersservice.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class UsersserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersserviceApplication.class, args);
	}

}
