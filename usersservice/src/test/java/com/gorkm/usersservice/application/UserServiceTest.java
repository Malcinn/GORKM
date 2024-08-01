package com.gorkm.usersservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest {

    @Autowired
    private UserService underTests;

    @Test
    public void shouldReturnUserDataForExistingUser() {
        underTests.getUserData("malcinn").subscribe(userResponse -> {
            Assertions.assertNotNull(userResponse);
            Assertions.assertInstanceOf(UserResponse.class, userResponse);
        });
    }

    @Test
    public void shouldNotReturnUserDataIfUserDoesNotExist() {
        underTests.getUserData("malcinn000000000000000").subscribe(Assertions::assertNull, throwable -> {
        });

    }
}
