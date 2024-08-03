package com.gorkm.usersservice.infrastructure.jdbc;

import com.gorkm.usersservice.TestContainersExtension;
import com.gorkm.usersservice.domain.UserApiStats;
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

import java.math.BigInteger;
import java.util.Optional;

@Testcontainers
@SpringBootTest
@ExtendWith(TestContainersExtension.class)
public class UserApiStatsRepositoryTest {

    @Container
    static final MySQLContainer<?> mySQLContainer;

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:8.0-debian"))
                .withDatabaseName("user-repository-integration-tests-db")
                .withUsername("testUser")
                .withPassword("testPassword")
                .withInitScript("schema.sql");
        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void setDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private UserApiStatsRepository underTests;

    @Test
    public void shouldReturnEmptyOptionalIfStatsForLoginDoesNotExist() {
        String login = "test";
        Optional<UserApiStats> result = underTests.findByLogin(login);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldCreateRequestCountForNewLogin() {
        String login = "test2";
        UserApiStats userApiStats = new UserApiStats(login, null, BigInteger.ONE.toString());
        underTests.save(userApiStats);
        Optional<UserApiStats> result = underTests.findByLogin(login);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(login, result.get().getLogin());
        Assertions.assertEquals(BigInteger.ONE, new BigInteger(result.get().getRequestCount()));
    }

    @Test
    public void shouldUpdateRequestCountForAlreadyExistingLogin() {
        String login = "test3";
        underTests.save(new UserApiStats(login, null, BigInteger.ONE.toString()));

        for (int i = 0; i < 9; i++) {
            underTests.findByLogin(login).ifPresent(entity -> {
                BigInteger nextRequestCount = new BigInteger(entity.getRequestCount()).add(BigInteger.ONE);
                underTests.updateByRequestCount(login, nextRequestCount);
            });
        }
        Optional<UserApiStats> result = underTests.findByLogin(login);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(login, result.get().getLogin());
        Assertions.assertEquals(BigInteger.valueOf(10L), new BigInteger(result.get().getRequestCount()));
    }

}
