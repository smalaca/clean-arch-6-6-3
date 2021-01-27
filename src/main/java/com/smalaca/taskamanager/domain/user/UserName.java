package com.smalaca.taskamanager.domain.user;

class UserName {
    private final String firstName;
    private final String lastName;

    UserName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }
}
