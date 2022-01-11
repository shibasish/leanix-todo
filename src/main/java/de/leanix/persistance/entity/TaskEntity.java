package de.leanix.persistance.entity;

import de.leanix.web.dto.SubtaskResponse;
import de.leanix.web.dto.TaskResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "TASK")
public class TaskEntity {
    @Id
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    @NotEmpty
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
        List<SubtaskResponse> subtaskResponses = new ArrayList<>();

        this.subTasks.stream().forEach( sub ->
                subtaskResponses.add(new SubtaskResponse(sub.getName(), Optional.of(sub.getDescription())))
            );

        return new TaskResponse(this.id, this.name, Optional.of(this.description), subtaskResponses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return id.equals(that.id) && name.equals(that.name) && Objects.equals(description, that.description) && Objects.equals(subTasks, that.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, subTasks);
    }
}
