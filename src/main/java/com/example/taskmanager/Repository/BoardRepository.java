package com.example.taskmanager.Repository;

import com.example.taskmanager.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
