package com.gorkm.usersservice;

import com.gorkm.usersservice.interfaces.facade.UserResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(TestContainersExtension.class)
public class UsersServiceIntegrationTests {

    private static final String USERS_URI = "users";

    @LocalServerPort
    private int port;

    @Value("${spring.security.user.name}")
    private String user;

    @Value("${spring.security.user.password}")
    private String password;

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient
                .bindToApplicationContext(this.context)
                .apply(springSecurity())
                .configureClient()
                .filter(basicAuthentication(user, password))
                .build();
    }

    @Test
    public void shouldReturnUserDataWithoutCalculationForMalcinnUser() {
        webTestClient
                .get()
                .uri(getLocalUrl() + "/malcinn")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponseDTO.class)
                .consumeWith(userResponseDTOEntityExchangeResult -> {
                    UserResponseDTO userResponseDTO = userResponseDTOEntityExchangeResult.getResponseBody();
                    Assertions.assertNotNull(userResponseDTO);
                    Assertions.assertEquals("11162108", userResponseDTO.id());
                    Assertions.assertEquals("Malcinn", userResponseDTO.login());
                    Assertions.assertEquals("Marcin", userResponseDTO.name());
                    Assertions.assertEquals("User", userResponseDTO.type());
                    Assertions.assertEquals("https://avatars.githubusercontent.com/u/11162108?v=4", userResponseDTO.avatarUrl());
                    Assertions.assertEquals("2015-02-23T16:11:01Z", userResponseDTO.createdAt());
                    Assertions.assertEquals(null, userResponseDTO.calculations());
                });
    }

    @Test
    public void shouldReturnUserDataWitProperCalculationForOctocatUser() {
        webTestClient
                .get()
                .uri(getLocalUrl() + "/octocat")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponseDTO.class)
                .consumeWith(userResponseDTOEntityExchangeResult -> {
                    UserResponseDTO userResponseDTO = userResponseDTOEntityExchangeResult.getResponseBody();
                    Assertions.assertNotNull(userResponseDTO);
                    Assertions.assertEquals("583231", userResponseDTO.id());
                    Assertions.assertEquals("octocat", userResponseDTO.login());
                    Assertions.assertEquals("The Octocat", userResponseDTO.name());
                    Assertions.assertEquals("User", userResponseDTO.type());
                    Assertions.assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", userResponseDTO.avatarUrl());
                    Assertions.assertEquals("2011-01-25T18:44:36Z", userResponseDTO.createdAt());
                    Assertions.assertTrue(userResponseDTO.calculations() > 0);
                });
    }

    @Test
    public void shouldReturn500statusCodeAndErrorMessageIfUserDoesNotExistOrBackendServiceIsNotAvailable() {
        webTestClient
                .get()
                .uri(getLocalUrl() + "/octocat_test_non_existing_user")
                .exchange()
                .expectStatus().is5xxServerError().expectBody(String.class)
                .consumeWith(Assertions::assertNotNull);
    }

    private String getLocalUrl() {
        return String.format("http://localhost:%s/%s", port, USERS_URI);
    }
}
