package com.gorkm.usersservice.interfaces.facade;

import reactor.core.publisher.Mono;

public interface UserFacade {

    Mono<UserResponseDTO> getUserData(String login);
}
