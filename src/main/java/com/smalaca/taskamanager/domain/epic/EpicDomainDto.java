package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.domain.project.ProjectDomainDto;
import com.smalaca.taskamanager.model.embedded.Owner;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EpicDomainDto {
    private final String title;
    private final String description;
    private final String toDoItemStatus;
    private final ProjectDomainDto project;
    private final Owner owner;
}
