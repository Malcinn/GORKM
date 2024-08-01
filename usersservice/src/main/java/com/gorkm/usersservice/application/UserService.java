package com.gorkm.usersservice.application;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> getUserData(String login);
}
