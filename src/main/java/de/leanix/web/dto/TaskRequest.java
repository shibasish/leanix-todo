package de.leanix.web.dto;

import de.leanix.persistance.entity.Subtask;
import de.leanix.persistance.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {
        private String name;
        private Optional<String> description;
        private List<TaskRequest> tasks;

        public TaskEntity toEntity() {
            List<Subtask> subTasks = new ArrayList<>();

            this.tasks.stream().forEach( taskRequest ->
                subTasks.add(new Subtask(taskRequest.name, taskRequest.description.get()))
            );

            return new TaskEntity(this.name, this.description.get(), subTasks);
        }
}
