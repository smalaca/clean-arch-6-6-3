package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.entities.Project;

public class EpicDomain {
    private final String title;
    private final String description;
    private ToDoItemStatus toDoItemStatus;
    private final Project project;
    private Owner owner;

    private EpicDomain(Builder builder) {
        title = builder.title;
        description = builder.description;
        toDoItemStatus = builder.toDoItemStatus;
        owner = builder.owner;
        project = builder.project;
    }

    public EpicDomainDto asDto() {
        return EpicDomainDto.builder()
                .title(title)
                .description(description)
                .toDoItemStatus(toDoItemStatus.name())
                .project(project)
                .owner(owner)
                .build();
    }

    static class Builder {
        private String title;
        private String description;
        private ToDoItemStatus toDoItemStatus;
        private Owner owner;
        private Project project;

        private Builder() {}

        static Builder epic() {
            return new Builder();
        }

        Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        Builder withStatus(String toDoItemStatus) {
            this.toDoItemStatus = ToDoItemStatus.valueOf(toDoItemStatus);
            return this;
        }

        void withOwner(Owner owner) {
            this.owner = owner;
        }

        void withProject(Project project) {
            this.project = project;
        }

        EpicDomain build() {
            return new EpicDomain(this);
        }
    }
}
