package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.entities.Project;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EpicDomainDto {
    private final String title;
    private final String description;
    private final String toDoItemStatus;
    private final Project project;
    private final Owner owner;
}
