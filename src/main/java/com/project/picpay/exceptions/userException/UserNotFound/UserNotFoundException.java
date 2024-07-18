package com.project.picpay.exceptions.userException.UserNotFound;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
