package com.gorkm.usersservice.interfaces.facade;

import com.gorkm.usersservice.application.CalculationService;
import com.gorkm.usersservice.application.UserResponse;
import com.gorkm.usersservice.application.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class.getName());

    private final UserService userService;

    private final CalculationService calculationService;

    @Override
    public Mono<UserResponseDTO> getUserData(String login) {
        return userService.getUserData(login).map(userResponse ->
                convert(userResponse, calculationService.calculate(userResponse))
        ).onErrorResume(throwable -> {
            LOGGER.error("Missing login param");
            return Mono.empty();
        });
    }

    public UserResponseDTO convert(UserResponse userResponse, Double calculation) {
        return new UserResponseDTO(userResponse.getId(),
                userResponse.getLogin(),
                userResponse.getName(),
                userResponse.getType(),
                userResponse.getAvatar_url(),
                userResponse.getCreated_at(),
                calculation);
    }
}
