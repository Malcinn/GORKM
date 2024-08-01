package com.gorkm.usersservice.interfaces.facade;

import com.gorkm.usersservice.application.UserService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    //private final CalculationService calculationService;

    @Override
    public Mono<UserResponseDTO> getUserData(String login) {
        //userData = userService.getUserData(login);
        //calculation = calculationService.calculate(userData);
        // userrepository store api execution
        return null;
    }
}
