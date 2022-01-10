package de.leanix.web.dto;

import de.leanix.persistance.entity.Subtask;
import de.leanix.persistance.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRequest {
        private String name;
        private Optional<String> description;
        private List<TaskRequest> tasks;

    public TaskRequest() {
    }

    public TaskRequest(String name, Optional<String> description, List<TaskRequest> tasks) {
            this.name = name;
            this.description = description;
            this.tasks = tasks;
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

        public List<TaskRequest> getTasks() {
            return tasks;
        }

        public void setTasks(List<TaskRequest> tasks) {
            this.tasks = tasks;
        }

        public TaskEntity toEntity() {
            List<Subtask> subTasks = new ArrayList<>();

            this.tasks.stream().forEach( taskRequest ->
                subTasks.add(new Subtask(taskRequest.name, taskRequest.description.get()))
            );

            return new TaskEntity(this.name, this.description.get(), subTasks);
        }
}
