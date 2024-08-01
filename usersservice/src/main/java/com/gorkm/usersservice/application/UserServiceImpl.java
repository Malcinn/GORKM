package com.gorkm.usersservice.application;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String GITHUB_API = "https://api.github.com/users/";
    private final WebClient webClient;


    @Override
    public Mono<UserResponse> getUserData(String login) {
        if (Objects.nonNull(login)) {
            webClient.get()
                    .uri(GITHUB_API + login)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(UserResponse.class);

        }
        return Mono.error(() -> new IllegalArgumentException("Missing login"));
    }
}
