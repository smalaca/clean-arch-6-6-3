package com.smalaca.taskamanager.domain.epic;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static RuntimeException notFound(Long ownerId) {
        return new UserException("User with id " + ownerId + " does not exist");
    }
}
