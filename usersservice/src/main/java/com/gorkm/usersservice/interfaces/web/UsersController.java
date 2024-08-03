package com.gorkm.usersservice.interfaces.web;

import com.gorkm.usersservice.application.event.UserAPICallEvent;
import com.gorkm.usersservice.application.exception.FetchUserException;
import com.gorkm.usersservice.interfaces.facade.UserFacade;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @CircuitBreaker(name = "USERS_API", fallbackMethod = "fallback")
    @GetMapping("/{login}")
    public ResponseEntity<Mono<?>> getUserData(@PathVariable String login) {
        applicationEventPublisher.publishEvent(new UserAPICallEvent(this, login));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userFacade.getUserData(login));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({FetchUserException.class})
    public String exceptionHandler(Exception exception) {
        return String.format("User does not exist or there is no connection with backend service, message from backend service:%s", exception.getMessage());
    }

    private ResponseEntity<Mono<?>> fallback(String param1, Exception exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON).body(Mono.just("Error, be aware!."));
    }


}
