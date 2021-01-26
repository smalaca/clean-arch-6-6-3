package com.smalaca.taskamanager.todoitemstate;

import com.google.common.collect.ImmutableMap;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;

import java.util.Map;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.APPROVED;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DEFINED;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DONE;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.IN_PROGRESS;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.RELEASED;

public class ToDoItemStatesFactory {
    public Map<ToDoItemStatus, ToDoItemState> getAll(
            StoryService storyService, EventsRegistry eventsRegistry,
            ProjectBacklogService projectBacklogService, CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        return ImmutableMap.of(
                APPROVED, new ToDoItemApprovedState(storyService, eventsRegistry),
                RELEASED, new ToDoItemReleasedState(eventsRegistry),
                DONE, new ToDoItemDoneState(storyService, eventsRegistry),
                IN_PROGRESS, new ToDoItemInProgressState(storyService),
                DEFINED, new ToDoItemDefinedState(projectBacklogService, communicationService, sprintBacklogService, eventsRegistry)
        );
    }
}
