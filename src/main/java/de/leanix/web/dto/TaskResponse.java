package de.leanix.web.dto;

import de.leanix.persistance.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {

    private UUID id;
    private String name;
    private Optional<String> description;
    private List<SubtaskResponse> tasks;

    public static TaskResponse toDto(TaskEntity taskEntity){
        List<SubtaskResponse> subTask = new ArrayList<>();

        taskEntity.getSubTasks().stream().forEach( subtask ->
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
