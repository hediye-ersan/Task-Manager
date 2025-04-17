package com.example.taskmanager.Service;

import com.example.taskmanager.Entity.Board;
import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // Otomatik Board oluştur
        Board board = new Board();
        board.setName("My Board");
        board.setUser(user); // Board'u kullanıcıyla ilişkilendir

        // Otomatik Columns oluştur
        BoardColumn todo = new BoardColumn();
        todo.setName("To Do");
        todo.setBoard(board);
        todo.setUser(user); // BoardColumn'u kullanıcıyla ilişkilendir

        BoardColumn inProgress = new BoardColumn();
        inProgress.setName("In Progress");
        inProgress.setBoard(board);
        inProgress.setUser(user); // BoardColumn'u kullanıcıyla ilişkilendir

        BoardColumn done = new BoardColumn();
        done.setName("Done");
        done.setBoard(board);
        done.setUser(user); // BoardColumn'u kullanıcıyla ilişkilendir

        List<BoardColumn> columns = new ArrayList<>();
        columns.add(todo);
        columns.add(inProgress);
        columns.add(done);
        board.setBoardColumns(columns);

        user.getBoards().add(board); // Cascade sayesinde board ve column'lar da kaydolur

        userRepository.save(user); // Tekrar kaydederek board ve column'ları persist et

        return jwtService.generateToken(user.getUsername());
    }



    public String login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtService.generateToken(username);
    }
}
