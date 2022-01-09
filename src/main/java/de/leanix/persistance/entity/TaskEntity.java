package de.leanix.persistance.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TaskEntity {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany
    @JoinTable(name = "SUBTASK", joinColumns = @JoinColumn(name = "TASK_ID"),
                inverseJoinColumns = @JoinColumn(name = "SUB_TASK_ID"))
    private List<TaskEntity> taskEntities = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskEntity> getTasks() {
        return taskEntities;
    }

    public void setTasks(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }
}
