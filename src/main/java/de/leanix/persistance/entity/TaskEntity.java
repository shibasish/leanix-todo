package de.leanix.persistance.entity;

import de.leanix.web.dto.SubtaskResponse;
import de.leanix.web.dto.TaskResponse;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskEntity {
    @Id
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    @NotEmpty
    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "SUBTASK", joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "id"))
    private List<Subtask> subTasks = new ArrayList<>();

    public TaskResponse toResponse() {
        List<SubtaskResponse> subtaskResponses = new ArrayList<>();

        this.subTasks.stream().forEach( sub ->
                subtaskResponses.add(new SubtaskResponse(sub.getName(), Optional.of(sub.getDescription())))
            );

        return new TaskResponse(this.id, this.name, Optional.of(this.description), subtaskResponses);
    }
}
