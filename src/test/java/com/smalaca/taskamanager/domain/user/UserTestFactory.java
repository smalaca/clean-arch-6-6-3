package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;

public class UserTestFactory {
    public static User create(String firstName, String lastName, String address, String phonePrefix, String phoneNumber) {
        User user = new User();
        UserName userName = new UserName();
        userName.setFirstName(firstName);
        userName.setLastName(lastName);
        user.setUserName(userName);
        user.setEmailAddress(emailAddress(address));
        user.setPhoneNumber(phoneNumber(phonePrefix, phoneNumber));

        return user;
    }

    private static PhoneNumber phoneNumber(String prefix, String number) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPrefix(prefix);
        phoneNumber.setNumber(number);
        return phoneNumber;
    }

    private static EmailAddress emailAddress(String address) {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmailAddress(address);
        return emailAddress;
    }
}
