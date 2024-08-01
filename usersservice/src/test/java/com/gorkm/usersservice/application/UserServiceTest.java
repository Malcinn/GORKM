package com.gorkm.usersservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService underTests;

    @Test
    public void shouldReturnUserDataForExistingUser() {
        underTests.getUserData("malcinn").subscribe(userResponse -> {
            Assertions.assertNotNull(userResponse);
            Assertions.assertInstanceOf(UserResponse.class, userResponse);
            UserResponse response = userResponse;
            Assertions.assertEquals("Malcinn", userResponse.getLogin());
            Assertions.assertEquals("11162108", userResponse.getId());
            Assertions.assertEquals("Malcinn", userResponse.getLogin());
            Assertions.assertEquals("Marcin", userResponse.getName());
            Assertions.assertEquals("User", userResponse.getType());
            Assertions.assertEquals("https://avatars.githubusercontent.com/u/11162108?v=4", userResponse.getAvatar_url());
            Assertions.assertEquals("2015-02-23T16:11:01Z", userResponse.getCreated_at());
        });
    }

    @Test
    public void shouldNotReturnUserDataIfUserDoesNotExist() {
        underTests.getUserData("malcinn000000000000000").subscribe(Assertions::assertNull, throwable -> {
        });

    }
}
