package com.example.taskmanager.Service;


import com.example.taskmanager.Dto.TaskDTO;
import com.example.taskmanager.Dto.TaskMapper;
import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Repository.BoardColumnRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    //TODO:Bu kısım tam anlamıyla çalışmıyor
    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public Task prepareTask(Task task) {
        if (task.getBoardColumn() == null) {
            BoardColumn defaultBoardColumn = boardColumnRepository.findById(1L)
                    .orElseThrow(() -> new IllegalStateException("Varsayılan BoardColumn bulunamadı!"));
            task.setBoardColumn(defaultBoardColumn);
        }
        return taskRepository.save(task); // Task kaydediliyor ve ID atanıyor
    }

    @Autowired
    private TaskMapper taskMapper;

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO); // DTO'yu entity'ye dönüştürdük
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask); // Entity'yi tekrar DTO'ya dönüştürdük
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDTO) // Her task'i DTO'ya dönüştürdük
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO); // Eğer task var ise, onu DTO'ya dönüştürdük
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    Task updatedTask = taskMapper.toEntity(taskDTO);
                    updatedTask.setId(existingTask.getId());
                    Task savedTask = taskRepository.save(updatedTask);
                    return taskMapper.toDTO(savedTask); // Güncellenmiş task'i DTO olarak döndürdük
                })
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
