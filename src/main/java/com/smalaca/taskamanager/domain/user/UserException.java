package com.smalaca.taskamanager.domain.user;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    static RuntimeException userAlreadyExists(String firstName, String lastName) {
        return new UserException("User " + firstName + " " + lastName + " already exists.");
    }
}
