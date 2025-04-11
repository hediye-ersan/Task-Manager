package com.example.taskmanager.Controller;

import com.example.taskmanager.Dto.TaskDTO;
import com.example.taskmanager.Dto.TaskMapper;
import com.example.taskmanager.Entity.Priority;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Service.TaskService;
import com.example.taskmanager.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO); // DTO'yu Task'e dönüştürüyoruz
        task = taskService.prepareTask(task); // prepareTask fonksiyonunu çağırıyoruz
        TaskDTO savedTaskDTO = taskService.createTask(taskMapper.toDTO(task)); // Task'i kaydediyoruz ve DTO'ya dönüştürüyoruz
        return savedTaskDTO; // Kaydedilen Task DTO olarak döndürülüyor
    }



    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO); // DTO ile işlem yapıyoruz
    }

    @PutMapping("/{taskId}/move/{boardColumnId}")
    public TaskDTO updateTaskColumn(@PathVariable Long taskId, @PathVariable Long boardColumnId) {
        Task updatedTask = taskService.updateTaskColumn(taskId, boardColumnId);
        return taskMapper.toDTO(updatedTask); // Güncellenmiş task DTO olarak döndürülür
    }


    @GetMapping("/tasks")
    public List<TaskDTO> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return tasks; // Burada zaten enum olarak döndürdük
    }

    @GetMapping("/priorities")
    public List<String> getAllPriorities() {
        List<String> priorities = Arrays.stream(Priority.values())
                .map(Enum::name)  // Enum değerlerini string olarak alıyoruz
                .collect(Collectors.toList());
        return priorities;
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
