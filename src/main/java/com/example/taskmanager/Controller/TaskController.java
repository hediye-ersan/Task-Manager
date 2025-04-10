package com.example.taskmanager.Controller;

import com.example.taskmanager.Dto.TaskDTO;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Service.TaskService;
import com.example.taskmanager.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.createTask(taskDTO); // DTO ile işlem yapıyoruz
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO); // DTO ile işlem yapıyoruz
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks(); // DTO'ları döndürüyoruz
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)); // DTO döndürüyoruz
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
