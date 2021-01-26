package com.smalaca.taskamanager.todoitemstate;

import com.smalaca.taskamanager.events.StoryApprovedEvent;
import com.smalaca.taskamanager.events.TaskApprovedEvent;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemApprovedState implements ToDoItemState {
    private final StoryService storyService;
    private final EventsRegistry eventsRegistry;

    public ToDoItemApprovedState(StoryService storyService, EventsRegistry eventsRegistry) {
        this.storyService = storyService;
        this.eventsRegistry = eventsRegistry;
    }

    @Override
    public void process(ToDoItem toDoItem) {
        if (toDoItem instanceof Story) {
            Story story = (Story) toDoItem;
            StoryApprovedEvent event = new StoryApprovedEvent();
            event.setStoryId(story.getId());
            eventsRegistry.publish(event);
        } else if (toDoItem instanceof Task) {
            Task task = (Task) toDoItem;

            if (task.isSubtask()) {
                TaskApprovedEvent event = new TaskApprovedEvent();
                event.setTaskId(task.getId());
                eventsRegistry.publish(event);
            } else {
                storyService.attachPartialApprovalFor(task.getStory().getId(), task.getId());
            }
        }
    }
}
