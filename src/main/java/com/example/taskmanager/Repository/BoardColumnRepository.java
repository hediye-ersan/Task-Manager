package com.example.taskmanager.Repository;

import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    Optional<BoardColumn> findByNameAndUser(String name, User user);
}
