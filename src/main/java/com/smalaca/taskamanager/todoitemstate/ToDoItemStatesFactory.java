package com.smalaca.taskamanager.todoitemstate;

import com.google.common.collect.ImmutableMap;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

import java.util.Map;

public class ToDoItemStatesFactory {
    public Map<ToDoItemStatus, ToDoItemState> getAll(StoryService storyService, EventsRegistry eventsRegistry) {
        return ImmutableMap.of(
                ToDoItemStatus.APPROVED, new ToDoItemApprovedState(storyService, eventsRegistry),
                ToDoItemStatus.RELEASED, new ToDoItemReleasedState(eventsRegistry)
        );
    }
}
