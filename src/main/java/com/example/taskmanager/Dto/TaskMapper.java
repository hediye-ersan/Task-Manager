package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setCreatedAt(taskDTO.createdAt());

        // BoardColumn ID'yi BoardColumn nesnesine dönüştürün
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setId(taskDTO.boardColumn());
        task.setBoardColumn(boardColumn);
        return task;
    }

    public TaskDTO toDTO(Task task) {
        // BoardColumn nesnesinin ID'sini alın
        Long boardColumnId = task.getBoardColumn() != null ? task.getBoardColumn().getId() : null;
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getCreatedAt(),
                boardColumnId
        );
    }
}


