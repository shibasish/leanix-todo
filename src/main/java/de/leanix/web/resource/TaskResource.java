package de.leanix.web.resource;

import com.codahale.metrics.annotation.Timed;
import de.leanix.web.dto.TaskResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    @GET
    @Path("/todos")
    @Timed
    public List<TaskResponse> getAllTask() {
        return null;
    }
}
