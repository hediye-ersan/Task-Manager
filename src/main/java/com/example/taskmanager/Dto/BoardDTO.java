package com.example.taskmanager.Dto;

import java.util.List;

public record  BoardDTO(Long id, String name, List<BoardColumnDTO> columns){
}
