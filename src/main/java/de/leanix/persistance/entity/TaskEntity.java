package de.leanix.persistance.entity;

import de.leanix.web.dto.SubtaskResponse;
import de.leanix.web.dto.TaskResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
public class TaskEntity {
    @Id
    @Column(nullable = false)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany
    @JoinTable(name = "SUBTASK", joinColumns = @JoinColumn(name = "TASK_ID"),
                inverseJoinColumns = @JoinColumn(name = "SUB_TASK_ID"))
    private List<TaskEntity> taskEntities = new ArrayList<>();

    public TaskEntity() {
    }

    public TaskEntity(String name, String description, List<TaskEntity> taskEntities) {
        this.name = name;
        this.description = description;
        this.taskEntities = taskEntities;
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

    public TaskResponse toResponse() {
        List<SubtaskResponse> subTask = new ArrayList<>();

        this.taskEntities.stream().forEach( taskRequest ->
            subTask.add(new SubtaskResponse(taskRequest.name, Optional.of(taskRequest.description)))
            );

        return new TaskResponse(this.id, this.name, Optional.of(this.description), subTask);
    }
}
