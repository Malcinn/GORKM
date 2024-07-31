package com.gorkm.usersservice;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(TestcontainersConfiguration.class)
/*
The @SpringBootTest annotation tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersServiceIntegrationTests {

    private static final String USERS_URI = "/users";
    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void shouldReturnUserDataWithoutCalculationForMalcinnUser() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(userResponseDTOEntityExchangeResult -> {
                    UserResponseDTO userResponseDTO = userResponseDTOEntityExchangeResult.getResponseBody();
                    Assertions.assertNotNull(userResponseDTO);
                    Assertions.assertEquals("11162108", userResponseDTO.getId());
                    Assertions.assertEquals("Malcinn", userResponseDTO.getLogin());
                    Assertions.assertEquals("Marcin", userResponseDTO.getName());
                    Assertions.assertEquals("User", userResponseDTO.getType());
                    Assertions.assertEquals("https://avatars.githubusercontent.com/u/11162108?v=4", userResponseDTO.getAvatarUrl());
                    Assertions.assertEquals("2015-02-23T16:11:01Z", userResponseDTO.getCreatedAt());
                    Assertions.assertEquals(null, userResponseDTO.getcalculations());
                    // null because liczba_followers dla tego usera to 0
                    // przez 0 nie można dzielić, wieć operacji calculations nie moża wykonać poprawnie.
                    // 6 / liczba_followers * (2 + liczba_public_repos)
                    //        6/0 * (2+8)
                });
    }

    @Test
    public void shouldReturnUserDataWitProperCalculationForOctocatUser() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(userResponseDTOEntityExchangeResult -> {
                    UserResponseDTO userResponseDTO = userResponseDTOEntityExchangeResult.getResponseBody();
                    Assertions.assertNotNull(userResponseDTO);
                    Assertions.assertEquals("583231", userResponseDTO.getId());
                    Assertions.assertEquals("octocat", userResponseDTO.getLogin());
                    Assertions.assertEquals("The Octocat", userResponseDTO.getName());
                    Assertions.assertEquals("User", userResponseDTO.getType());
                    Assertions.assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", userResponseDTO.getAvatarUrl());
                    Assertions.assertEquals("2011-01-25T18:44:36Z", userResponseDTO.getCreatedAt());
                    Assertions.assertTrue(userResponseDTO.getcalculations() > 0);
                    // null because liczba_followers dla tego usera to 0
                    // przez 0 nie można dzielić, wieć operacji calculations nie moża wykonać poprawnie.
                    // 6 / liczba_followers * (2 + liczba_public_repos)
                    //        6/0 * (2+8)
                });
    }


    @Test
    public void shouldStoreAPIExecutionCountForEachUser() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat")
                .exchange()
    }

    @Test
    public void shouldReturn4XXHttpCodeWithMessageForUserThatDoNotExist() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat_test_non_existing_user")
                .exchange()
                .expectStatus().is4xxClientError().expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    Assert.assertNotNull(responseBody);
                    Assert.assertEquals("No Connection With DB", responseBody);
                });
    }

    @Test
    public void shouldThrow5XXWhenThereIsNoConnectionWithDB() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat")
                .exchange()
                .expectStatus().is5xxServerError().expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    Assert.assertNotNull(responseBody);
                    Assert.assertEquals("No Connection Wit DB", responseBody);
                });
    }


    @Test
    public void shouldReturn5XXWIthMessageIfThereIsNoConnectionWithBackendService() {
        webTestClient
                .get()
                .uri(USERS_URI + "/octocat")
                .exchange()
                .expectStatus().is5xxServerError().expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    Assert.assertNotNull(responseBody);
                    Assert.assertEquals("No Connection Wit git service", responseBody);
                });
    }
}
