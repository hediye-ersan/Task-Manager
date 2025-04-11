package com.example.taskmanager.Controller;

import com.example.taskmanager.Dto.BoardDTO;
import com.example.taskmanager.Entity.Board;
import com.example.taskmanager.Service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @GetMapping
    public List<BoardDTO> getBoards(HttpServletRequest request) {
        return boardService.getBoardsForUser(request);
    }

    @GetMapping("/{id}")
    public Optional<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}
