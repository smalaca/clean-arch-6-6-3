package com.smalaca.taskamanager.domain.owner;

import com.smalaca.taskamanager.domain.phonenumber.PhoneNumber;

public class OwnerDomain {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final PhoneNumber phoneNumber;

    private OwnerDomain(String firstName, String lastName, String emailAddress, PhoneNumber phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public OwnerDomainDto asDto() {
        return OwnerDomainDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .phoneNumber(phoneNumber.getNumber())
                .phonePrefix(phoneNumber.getPrefix())
                .build();
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private String emailAddress;
        private PhoneNumber phoneNumber;

        private Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static Builder owner(String firstName, String lastName) {
            return new Builder(firstName, lastName);
        }

        public OwnerDomain build() {
            return new OwnerDomain(firstName, lastName, emailAddress, phoneNumber);
        }

        public void withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public void withPhoneNumber(PhoneNumber phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
