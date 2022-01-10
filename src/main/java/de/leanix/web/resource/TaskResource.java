package de.leanix.web.resource;

import de.leanix.persistance.entity.TaskEntity;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.dto.TaskResponse;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    private final TaskRepository taskRepository;

    @Inject
    public TaskResource(final TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GET
    @UnitOfWork
    public List<TaskResponse> getAllTask() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::toDto)
                .collect(Collectors.toList());

    }

    @POST
    @UnitOfWork
    public TaskResponse createTask(TaskEntity taskEntity) {
        return taskRepository.createTask(taskEntity).toResponse();
    }
}
