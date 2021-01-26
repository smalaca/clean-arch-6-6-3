package com.smalaca.taskamanager.todoitemstate;

import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;

public class ToDoItemReleasedState {
    private final EventsRegistry eventsRegistry;

    public ToDoItemReleasedState(EventsRegistry eventsRegistry) {
        this.eventsRegistry = eventsRegistry;
    }

    public void process(ToDoItem toDoItem) {
        ToDoItemReleasedEvent event = new ToDoItemReleasedEvent();
        event.setToDoItemId(toDoItem.getId());
        eventsRegistry.publish(event);
    }
}
