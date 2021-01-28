package com.smalaca.taskamanager.domain.user;

import javax.persistence.Embeddable;

@Embeddable
class UserName {
    private String firstName;
    private String lastName;

    private UserName() {}

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
