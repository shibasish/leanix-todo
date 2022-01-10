package de.leanix.web.resource;

import de.leanix.persistance.entity.TaskEntity;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.dto.TaskResponse;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @GET
    @Path("/{id}")
    @UnitOfWork
    public TaskResponse getTaskWithId(@PathParam("id") UUID uuid) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(uuid);

        return taskEntity.isPresent() ? taskEntity.get().toResponse() : null;

    }

    @POST
    @UnitOfWork
    public TaskResponse createTask(TaskEntity taskEntity) {
        return taskRepository.createTask(taskEntity).toResponse();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response.Status deleteTask(@PathParam("id") UUID uuid) {
        return taskRepository.deleteTask(uuid) ? Response.Status.OK : Response.Status.BAD_REQUEST;
    }

}
