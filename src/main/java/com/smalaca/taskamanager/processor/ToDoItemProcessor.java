package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.todoitemstate.ToDoItemState;
import com.smalaca.taskamanager.todoitemstate.ToDoItemStatesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ToDoItemProcessor {
    @Autowired private StoryService storyService;
    @Autowired private EventsRegistry eventsRegistry;
    @Autowired private ProjectBacklogService projectBacklogService;
    @Autowired private CommunicationService communicationService;
    @Autowired private SprintBacklogService sprintBacklogService;

    private Map<ToDoItemStatus, ToDoItemState> states;

    public ToDoItemProcessor() {}

    ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        this.storyService = storyService;
        this.eventsRegistry = eventsRegistry;
        this.projectBacklogService = projectBacklogService;
        this.communicationService = communicationService;
        this.sprintBacklogService = sprintBacklogService;
    }

    public void processFor(ToDoItem toDoItem) {
        initToDoItemStates();

        states.get(toDoItem.getStatus()).process(toDoItem);
    }

    private void initToDoItemStates() {
        if (states == null) {
            states = new ToDoItemStatesFactory().getAll(storyService, eventsRegistry, projectBacklogService, communicationService, sprintBacklogService);
        }
    }
}
