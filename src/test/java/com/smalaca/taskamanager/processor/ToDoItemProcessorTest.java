package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.RELEASED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class ToDoItemProcessorTest {
    private static final Long TO_DO_ITEM_ID = 123L;

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
}