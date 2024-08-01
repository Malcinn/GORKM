package com.gorkm.usersservice.interfaces.facade;

import com.gorkm.usersservice.application.CalculationService;
import com.gorkm.usersservice.application.UserResponse;
import com.gorkm.usersservice.application.UserService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    private final CalculationService calculationService;

    @Override
    public Mono<UserResponseDTO> getUserData(String login) {
        // userrepository store api execution
        return userService.getUserData(login).map(userResponse ->
                map(userResponse, calculationService.calculate(userResponse))
        );
    }

    public UserResponseDTO map(UserResponse userResponse, Double calculation) {
        return new UserResponseDTO(userResponse.getId(),
                userResponse.getLogin(),
                userResponse.getName(),
                userResponse.getType(),
                userResponse.getAvatar_url(),
                userResponse.getCreated_at(),
                calculation);
    }
}
