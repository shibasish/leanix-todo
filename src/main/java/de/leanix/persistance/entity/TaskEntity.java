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
@Table(name = "TASK")
public class TaskEntity {
    @Id
    @Column(nullable = false)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "SUBTASK", joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "id"))
    private List<Subtask> subTasks = new ArrayList<>();

    public TaskEntity() {
    }

    public TaskEntity(String name, String description, List<Subtask> subTasks) {
        this.name = name;
        this.description = description;
        this.subTasks = subTasks;
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

    public List<Subtask> getTasks() {
        return subTasks;
    }

    public void setTasks(List<Subtask> subTasks) {
        this.subTasks = subTasks;
    }

    public TaskResponse toResponse() {
        List<SubtaskResponse> subTasks = new ArrayList<>();

        this.subTasks.stream().forEach( sub ->
                subTasks.add(new SubtaskResponse(sub.getName(), Optional.of(sub.getDescription())))
            );

        return new TaskResponse(this.id, this.name, Optional.of(this.description), subTasks);
    }
}
