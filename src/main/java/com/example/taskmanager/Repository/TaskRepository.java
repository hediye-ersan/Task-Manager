package com.example.taskmanager.Repository;

import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
