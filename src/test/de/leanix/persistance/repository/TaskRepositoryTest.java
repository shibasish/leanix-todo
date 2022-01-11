package de.leanix.persistance.repository;

import de.leanix.persistance.entity.Subtask;
import de.leanix.persistance.entity.TaskEntity;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskRepositoryTest {

    public DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(TaskEntity.class).build();

    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository = new TaskRepository(database.getSessionFactory());
    }

    @Test
    void createTaskShouldReturnNewTaskEntity() {
        final TaskEntity taskEntity = database.inTransaction(
                () -> taskRepository.createTask(new TaskEntity("name", "desc", null)));
       assertEquals("name", taskEntity.getName());
       assertEquals("desc", taskEntity.getDescription());
       assertNull(taskEntity.getClass());
       assertEquals(taskEntity, taskRepository.findById(taskEntity.getId()).get());
    }

    @Test
    void findAllShouldReturnAllTheTasks() {
        database.inTransaction(() -> {
            taskRepository.createTask(new TaskEntity("name-1", "desc-1", null));

            List<Subtask> subtasks = Collections.singletonList(new Subtask("subtask-2",""));
            taskRepository.createTask(new TaskEntity("name-2", "desc-2", subtasks));

            taskRepository.createTask(new TaskEntity("name-3", "desc-3", null));
        });

        final List<TaskEntity> taskEntities = taskRepository.findAll();

        assertEquals(3, taskEntities.size());
    }

    @Test
    void findByIdShouldReturnTheTask() {
        final TaskEntity taskEntity = database.inTransaction(() ->
            taskRepository.createTask(new TaskEntity("name-1", "desc-1", null))
        );

        assertEquals(taskEntity, taskRepository.findById(taskEntity.getId()).get());
    }

    @Test
    void updateShouldUpdateTheTaskWhenIdGiven() {
        final TaskEntity taskEntity = database.inTransaction(() ->
                taskRepository.createTask(new TaskEntity("name-1", "desc-1", null))
        );

        TaskEntity updatedTaskEntity = new TaskEntity("name-1-updated", "desc-1", null);

        Optional<TaskEntity> actualTaskEntity = taskRepository.updateTask(taskEntity.getId(), updatedTaskEntity);

        assertEquals(updatedTaskEntity.getName(), actualTaskEntity.get().getName());
        assertEquals(updatedTaskEntity.getDescription(), actualTaskEntity.get().getDescription());
        assertNull(actualTaskEntity.get().getSubTasks());
    }

    @Test
    void deleteShouldDeleteTheTask() {
        final TaskEntity taskEntity = database.inTransaction(() ->
                taskRepository.createTask(new TaskEntity("name-1", "desc-1", null))
        );

        Boolean result = taskRepository.deleteTask(taskEntity.getId());

        assertEquals(true, result);
        assertEquals(Optional.empty(), taskRepository.findById(taskEntity.getId()));
    }
}