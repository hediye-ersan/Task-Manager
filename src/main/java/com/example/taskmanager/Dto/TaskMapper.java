package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.BoardColumnRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final BoardColumnRepository boardColumnRepository;

    public TaskMapper(BoardColumnRepository boardColumnRepository) {
        this.boardColumnRepository = boardColumnRepository;
    }


    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setCreatedAt(taskDTO.createdAt());

        // BoardColumn'u name ile bul ve set et
        BoardColumn boardColumn = boardColumnRepository.findByName(taskDTO.boardColumnName())
                .orElseThrow(() -> new RuntimeException("BoardColumn not found with name: " + taskDTO.boardColumnName()));
        task.setBoardColumn(boardColumn);
        return task;
    }

    public TaskDTO toDTO(Task task) {
        String boardColumnName = task.getBoardColumn() != null ? task.getBoardColumn().getName() : null;
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getCreatedAt(),
                boardColumnName
        );
    }
}


