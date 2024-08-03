package com.gorkm.usersservice.interfaces.web;

import com.gorkm.usersservice.application.event.UserAPICallEvent;
import com.gorkm.usersservice.interfaces.facade.UserFacade;
import com.gorkm.usersservice.interfaces.facade.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/{login}")
    public ResponseEntity<Mono<UserResponseDTO>> getUserData(@PathVariable String login) {
        applicationEventPublisher.publishEvent(new UserAPICallEvent(this, login));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userFacade.getUserData(login));
    }

}
