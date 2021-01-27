package com.smalaca.taskamanager.domain.owner;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OwnerDomainDto {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String phoneNumber;
    private final String phonePrefix;
}
