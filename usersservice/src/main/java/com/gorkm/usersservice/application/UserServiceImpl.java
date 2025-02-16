package com.gorkm.usersservice.application;

import com.gorkm.usersservice.application.exception.FetchUserException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    private static final String GITHUB_API = "https://api.github.com/users/";

    private final WebClient webClient;

    @Cacheable("userData")
    @Override
    public Mono<UserResponse> getUserData(final String login) {
        if (Objects.nonNull(login)) {
            return webClient.get()
                    .uri(GITHUB_API + login)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .onErrorResume(throwable -> {
                        LOGGER.error("User does not exist or there is no connection with backend service");
                        return Mono.error(new FetchUserException(throwable.getMessage()));
                    });

        }
        return Mono.error(() -> new IllegalArgumentException("Missing login param"));
    }
}
