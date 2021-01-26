package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.events.StoryApprovedEvent;
import com.smalaca.taskamanager.events.StoryDoneEvent;
import com.smalaca.taskamanager.events.TaskApprovedEvent;
import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.APPROVED;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DONE;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.RELEASED;
import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.TO_BE_DEFINED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class ToDoItemProcessorTest {
    private static final Long TO_DO_ITEM_ID = 123L;
    private static final Long STORY_ID = 246L;
    private static final ToDoItemStatus NOT_DONE = TO_BE_DEFINED;

    private final StoryService storyService = mock(StoryService.class);
    private final EventsRegistry eventsRegistry = mock(EventsRegistry.class);
    private final ProjectBacklogService projectBacklogService = mock(ProjectBacklogService.class);
    private final CommunicationService communicationService = mock(CommunicationService.class);
    private final SprintBacklogService sprintBacklogService = mock(SprintBacklogService.class);
    private final ToDoItemProcessor processor = new ToDoItemProcessor(
            storyService, eventsRegistry, projectBacklogService, communicationService, sprintBacklogService);

    @Test
    void shouldProcessReleasedToDoItem() {
        ArgumentCaptor<ToDoItemReleasedEvent> captor = ArgumentCaptor.forClass(ToDoItemReleasedEvent.class);
        ToDoItem toDoItem = mock(ToDoItem.class);
        given(toDoItem.getStatus()).willReturn(RELEASED);
        given(toDoItem.getId()).willReturn(TO_DO_ITEM_ID);

        processor.processFor(toDoItem);

        then(eventsRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getToDoItemId()).isEqualTo(TO_DO_ITEM_ID);
        verifyNoMoreInteractions(eventsRegistry);
        verifyNoInteractions(storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessApprovedStory() {
        ArgumentCaptor<StoryApprovedEvent> captor = ArgumentCaptor.forClass(StoryApprovedEvent.class);
        Story story = mock(Story.class);
        given(story.getStatus()).willReturn(APPROVED);
        given(story.getId()).willReturn(TO_DO_ITEM_ID);

        processor.processFor(story);

        then(eventsRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getStoryId()).isEqualTo(TO_DO_ITEM_ID);
        verifyNoMoreInteractions(eventsRegistry);
        verifyNoInteractions(storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldNotProcessApprovedEpic() {
        Epic epic = mock(Epic.class);
        given(epic.getStatus()).willReturn(APPROVED);

        processor.processFor(epic);

        verifyNoInteractions(eventsRegistry, storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessApprovedTask() {
        Task task = mock(Task.class);
        given(task.getStatus()).willReturn(APPROVED);
        given(task.getId()).willReturn(TO_DO_ITEM_ID);
        given(task.isSubtask()).willReturn(false);
        Story story = mock(Story.class);
        given(story.getId()).willReturn(STORY_ID);
        given(task.getStory()).willReturn(story);

        processor.processFor(task);

        then(storyService).should().attachPartialApprovalFor(STORY_ID, TO_DO_ITEM_ID);
        verifyNoMoreInteractions(storyService);
        verifyNoInteractions(eventsRegistry, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessApprovedSubTask() {
        ArgumentCaptor<TaskApprovedEvent> captor = ArgumentCaptor.forClass(TaskApprovedEvent.class);
        Task task = mock(Task.class);
        given(task.getStatus()).willReturn(APPROVED);
        given(task.isSubtask()).willReturn(true);
        given(task.getId()).willReturn(TO_DO_ITEM_ID);

        processor.processFor(task);

        then(eventsRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getTaskId()).isEqualTo(TO_DO_ITEM_ID);
        verifyNoMoreInteractions(eventsRegistry);
        verifyNoInteractions(storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessDoneEpic() {
        Epic epic = mock(Epic.class);
        given(epic.getStatus()).willReturn(DONE);

        processor.processFor(epic);

        verifyNoInteractions(eventsRegistry, storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessDoneStory() {
        ArgumentCaptor<StoryDoneEvent> captor = ArgumentCaptor.forClass(StoryDoneEvent.class);
        Story story = mock(Story.class);
        given(story.getStatus()).willReturn(DONE);
        given(story.getId()).willReturn(TO_DO_ITEM_ID);

        processor.processFor(story);

        then(eventsRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getStoryId()).isEqualTo(TO_DO_ITEM_ID);
        verifyNoMoreInteractions(eventsRegistry);
        verifyNoInteractions(storyService, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessDoneTask() {
        Task task = mock(Task.class);
        given(task.getStatus()).willReturn(DONE);
        Story story = mock(Story.class);
        given(story.getStatus()).willReturn(NOT_DONE);
        given(task.getStory()).willReturn(story);

        processor.processFor(task);

        then(storyService).should().updateProgressOf(story, task);
        verifyNoMoreInteractions(storyService);
        verifyNoInteractions(eventsRegistry, projectBacklogService, communicationService, sprintBacklogService);
    }

    @Test
    void shouldProcessDoneTaskWhenStoryIsDone() {
        ArgumentCaptor<StoryDoneEvent> captor = ArgumentCaptor.forClass(StoryDoneEvent.class);
        Task task = mock(Task.class);
        given(task.getStatus()).willReturn(DONE);
        Story story = mock(Story.class);
        given(story.getStatus()).willReturn(DONE);
        given(story.getId()).willReturn(STORY_ID);
        given(task.getStory()).willReturn(story);

        processor.processFor(task);

        then(storyService).should().updateProgressOf(story, task);
        then(eventsRegistry).should().publish(captor.capture());
        assertThat(captor.getValue().getStoryId()).isEqualTo(STORY_ID);
        verifyNoMoreInteractions(storyService, eventsRegistry);
        verifyNoInteractions(projectBacklogService, communicationService, sprintBacklogService);
    }
}