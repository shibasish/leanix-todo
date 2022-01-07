package de.leanix.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Task {

    UUID id;
    String name;
    String description;
    List<Task> tasks;

    public Task(UUID id, String name, String description, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    @JsonProperty
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
