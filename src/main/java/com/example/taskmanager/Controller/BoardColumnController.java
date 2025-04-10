package com.example.taskmanager.Controller;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Service.BoardColumnService;
import com.example.taskmanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/board-columns")

public class BoardColumnController {

    @Autowired
    private BoardColumnService boardColumnService;

    @PostMapping
    public BoardColumn createBoardColumn(@RequestBody BoardColumn boardColumn) {
        return boardColumnService.createBoardColumn(boardColumn);
    }

    @GetMapping
    public List<BoardColumn> getAllBoardColumns() {
        return boardColumnService.getAllBoardColumns();
    }

    @GetMapping("/{id}")
    public BoardColumn getBoardColumnById(@PathVariable Long id) {
        return boardColumnService.getBoardColumnById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BoardColumn not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteBoardColumn(@PathVariable Long id) {
        boardColumnService.deleteBoardColumn(id);
    }
}
