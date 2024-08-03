package com.gorkm.usersservice.application.event;

import com.gorkm.usersservice.TestContainersExtension;
import com.gorkm.usersservice.domain.UserApiStats;
import com.gorkm.usersservice.infrastructure.jdbc.UserApiStatsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

@SpringBootTest
@ExtendWith(TestContainersExtension.class)
public class UserAPICallEventListenerTest {

    @Autowired
    private UserAPICallEventListener userAPICallEventListener;

    @Autowired
    private UserApiStatsRepository userApiStatsRepository;

    @Test
    public void shouldThrowExceptionWhenEventIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> userAPICallEventListener.onApplicationEvent(null),
                " Exception expected, but it wasn't thrown");
    }

    @Test
    public void shouldCreateRequestCountForNewLogin() {
        String login = "test";
        UserAPICallEvent userAPICallEvent = new UserAPICallEvent(this, login);
        userAPICallEventListener.onApplicationEvent(userAPICallEvent);
        Optional<UserApiStats> result = userApiStatsRepository.findByLogin(login);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("1", result.get().getRequestCount());
    }

    @Test
    public void shouldUpdateRequestCountForAlreadyExistingLogin() {
        String login = "test2";
        UserAPICallEvent userAPICallEvent = new UserAPICallEvent(this, login);
        for (int i = 0; i < 10; i++) {
            userAPICallEventListener.onApplicationEvent(userAPICallEvent);
        }
        Optional<UserApiStats> result = userApiStatsRepository.findByLogin(login);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("10", result.get().getRequestCount());
    }

}
