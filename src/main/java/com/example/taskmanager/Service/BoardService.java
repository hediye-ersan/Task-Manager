package com.example.taskmanager.Service;

import com.example.taskmanager.Dto.BoardDTO;
import com.example.taskmanager.Dto.BoardMapper;
import com.example.taskmanager.Entity.Board;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.BoardRepository;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.Security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public BoardService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<BoardDTO> getBoardsForUser(HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBoards().stream()
                .map(BoardMapper::toDto)
                .collect(Collectors.toList());
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

}
