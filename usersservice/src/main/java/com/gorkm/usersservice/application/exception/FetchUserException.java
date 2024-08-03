package com.gorkm.usersservice.application.exception;

public class FetchUserException extends Exception {
    public FetchUserException() {
    }

    public FetchUserException(String message) {
        super(message);
    }
}
