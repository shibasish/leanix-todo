package de.leanix.web.resource;

import de.leanix.persistance.entity.TaskEntity;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.dto.TaskResponse;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskResourceTest {

    private static final TaskRepository taskRepository = mock(TaskRepository.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TaskResource(taskRepository))
            .build();
    private TaskEntity taskEntity = mock(TaskEntity.class);
    private TaskResponse response;
    private UUID id = UUID.randomUUID();
    @BeforeEach
    void setUp() {
        response = new TaskResponse(id, "task", Optional.empty(), null);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPersonSuccess() {

        List<TaskResponse> res = Collections.singletonList(response);

        when(taskEntity.getId()).thenReturn(id);
        when(taskEntity.getDescription()).thenReturn(null);
        when(taskEntity.getName()).thenReturn("task");
        when(taskEntity.getTasks()).thenReturn(null);

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(taskEntity));

        List<TaskResponse> found = EXT.target("/todos").request().get(new GenericType<List<TaskResponse>>() {
        });

        assertEquals(found.get(0).getId(), taskEntity.getId());
    }
}