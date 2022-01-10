package de.leanix.web.resource;

import de.leanix.persistance.entity.TaskEntity;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.dto.TaskRequest;
import de.leanix.web.dto.TaskResponse;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskResourceTest {

    private static final TaskRepository taskRepository = mock(TaskRepository.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TaskResource(taskRepository))
            .build();
    private TaskEntity taskEntity;
    private TaskEntity subtask;
    private TaskResponse response;
    private final static UUID id = UUID.randomUUID();
    private ArgumentCaptor<TaskEntity> taskEntityArgumentCaptor = ArgumentCaptor.forClass(TaskEntity.class);

    @BeforeEach
    void setUp() {
        subtask = new TaskEntity("subtask", "desc", null);
        taskEntity = new TaskEntity("task", "desc", Collections.singletonList(subtask));
        response = new TaskResponse(id, "task", Optional.empty(), null);
    }

    @AfterEach
    void tearDown() {
        reset(taskRepository);
    }

    @Test
    void getAllTasks() {

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(taskEntity));

        List<TaskResponse> response = EXT.target("/todos")
                .request()
                .get(new GenericType<List<TaskResponse>>() {});

        assertEquals(1, response.size());
    }

    @Test
    void createTask() {
        when(taskRepository.createTask(any(TaskEntity.class))).thenReturn(taskEntity);

        Response response = EXT.target("/todos")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(taskEntity, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(taskRepository).createTask(taskEntityArgumentCaptor.capture());
        assertEquals(taskEntityArgumentCaptor.getValue().getId(), taskEntity.getId());
    }
}