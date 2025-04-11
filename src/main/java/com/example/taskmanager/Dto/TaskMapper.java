package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setCreatedAt(taskDTO.createdAt());
        return task;
    }

    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getCreatedAt()
        );
    }
}
