package com.gorkm.usersservice.interfaces.web;

import com.gorkm.usersservice.interfaces.facade.UserFacade;
import com.gorkm.usersservice.interfaces.facade.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public Mono<ServerResponse> getUserData(@RequestParam String login) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userFacade.getUserData(login), UserResponseDTO.class);
    }

}
