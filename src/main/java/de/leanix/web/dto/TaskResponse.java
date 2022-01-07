package de.leanix.web.dto;

import de.leanix.domain.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskResponse {

    UUID id;
    String name;
    String description;
    List<Task> tasks;


}
