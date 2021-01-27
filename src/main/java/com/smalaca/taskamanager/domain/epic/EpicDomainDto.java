package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.domain.owner.OwnerDomainDto;
import com.smalaca.taskamanager.domain.project.ProjectDomainDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EpicDomainDto {
    private final String title;
    private final String description;
    private final String toDoItemStatus;
    private final ProjectDomainDto project;
    private final OwnerDomainDto owner;
}
