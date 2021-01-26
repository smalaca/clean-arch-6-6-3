package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;

class EpicBuilder {
    private final Epic epic;

    private EpicBuilder(Epic epic) {
        this.epic = epic;
    }
    
    static EpicBuilder epic() {
        return new EpicBuilder(new Epic());
    }

    EpicBuilder withTitle(String title) {
        epic.setTitle(title);
        return this;
    }

    EpicBuilder withDescription(String description) {
        epic.setDescription(description);
        return this;
    }

    EpicBuilder withStatus(String toDoItemStatus) {
        epic.setStatus(ToDoItemStatus.valueOf(toDoItemStatus));
        return this;
    }

    void withOwner(Owner owner) {
        epic.setOwner(owner);
    }

    void withProject(Project project) {
        epic.assignProject(project);
    }

    Epic build() {
        return epic;
    }
}
