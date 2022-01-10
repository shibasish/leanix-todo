package de.leanix.web.dto;

import de.leanix.persistance.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskResponse {

    private UUID id;
    private String name;
    private Optional<String> description;
    private List<SubtaskResponse> tasks;

    public TaskResponse() {
    }

    public TaskResponse(UUID id, String name, Optional<String> description, List<SubtaskResponse> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public List<SubtaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<SubtaskResponse> tasks) {
        this.tasks = tasks;
    }

    public static TaskResponse toDto(TaskEntity taskEntity){
        List<SubtaskResponse> subTask = new ArrayList<>();

        taskEntity.getTasks().stream().forEach( subtask ->
            subTask.add(new SubtaskResponse(subtask.getName(), Optional.of(subtask.getDescription())))
        );

        return new TaskResponse(
                taskEntity.getId(),
                taskEntity.getName(),
                taskEntity.getDescription() == null ? Optional.empty() : Optional.of(taskEntity.getDescription()),
                subTask
        );
    }
}
