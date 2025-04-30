package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.BoardColumnRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setCreatedAt(taskDTO.createdAt());
        task.setDueDate(taskDTO.dueDate());

        // boardColumn ve user servis katmanında set edilecek
        return task;
    }

    public TaskDTO toDTO(Task task) {
        String boardColumnName = task.getBoardColumn() != null ? task.getBoardColumn().getName() : null;
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getCreatedAt(),
                boardColumnName,
                task.getDueDate()
        );
    }
}



