package com.gorkm.usersservice.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserAPICallEvent extends ApplicationEvent {

    private final String login;

    public UserAPICallEvent(Object source, String login) {
        super(source);
        this.login = login;
    }
}
