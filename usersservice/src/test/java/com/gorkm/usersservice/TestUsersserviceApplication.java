package com.gorkm.usersservice;

import org.springframework.boot.SpringApplication;

public class TestUsersserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(UsersserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
