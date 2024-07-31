package com.gorkm.usersservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class UsersServiceIntegrationTests {

    @Test
    public void shouldReturnUserDataForExistingUser() {

    }

    @Test
    public void shouldReturnProperCalculationForUser() {

    }

    @Test
    public void shouldStoreAPIExecutionCountForEachUser() {

    }

    @Test
    public void shouldThrow5XXWhenThereIsNoConnectionWithDB() {

    }

    @Test
    public void shouldReturn4XXHttpCodeWithMessageForUserThatDoNotExist() {

    }

    @Test
    public void shouldReturn5XXWIthMessageIfThereIsNoConnectionWithBackendService() {

    }
}
