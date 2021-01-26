package com.smalaca.taskamanager.todoitemstate;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public interface ToDoItemState {
    void process(ToDoItem toDoItem);
}
