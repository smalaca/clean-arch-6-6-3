package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;

public class EpicBuilder {
    private final Epic epic;

    private EpicBuilder(Epic epic) {
        this.epic = epic;
    }
    
    public static EpicBuilder epic() {
        return new EpicBuilder(new Epic());
    }

    public EpicBuilder withTitle(String title) {
        epic.setTitle(title);
        return this;
    }

    public EpicBuilder withDescription(String description) {
        epic.setDescription(description);
        return this;
    }

    public EpicBuilder withStatus(String toDoItemStatus) {
        epic.setStatus(ToDoItemStatus.valueOf(toDoItemStatus));
        return this;
    }

    public void withOwner(Owner owner) {
        epic.setOwner(owner);
    }

    public void withProject(Project project) {
        epic.assignProject(project);
    }

    public Epic build() {
        return epic;
    }
}
