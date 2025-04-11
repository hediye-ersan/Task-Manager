package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.Board;
import com.example.taskmanager.Entity.BoardColumn;

import java.util.List;
import java.util.stream.Collectors;

public class BoardMapper {
    public static BoardDTO toDto(Board board) {
        List<BoardColumnDTO> columns = board.getBoardColumns().stream()
                .map(BoardMapper::toDto)
                .collect(Collectors.toList());

        return new BoardDTO(board.getId(), board.getName(), columns);
    }

    public static BoardColumnDTO toDto(BoardColumn column) {
        return new BoardColumnDTO(column.getId(), column.getName());
    }
}
