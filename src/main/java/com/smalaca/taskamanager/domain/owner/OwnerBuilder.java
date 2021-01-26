package com.smalaca.taskamanager.domain.owner;

import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;

public class OwnerBuilder {
    private final Owner owner;

    private OwnerBuilder(Owner owner) {
        this.owner = owner;
    }

    public static OwnerBuilder owner(String firstName, String lastName) {
        Owner owner = new Owner();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);

        return new OwnerBuilder(owner);
    }

    public Owner build() {
        return owner;
    }

    public void withEmailAddress(EmailAddress address) {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmailAddress(address.getEmailAddress());
        owner.setEmailAddress(emailAddress);
    }

    public void withPhoneNumber(PhoneNumber phoneNumber) {
        PhoneNumber phone = new PhoneNumber();
        phone.setPrefix(phoneNumber.getPrefix());
        phone.setNumber(phoneNumber.getNumber());

        owner.setPhoneNumber(phoneNumber);
    }
}
