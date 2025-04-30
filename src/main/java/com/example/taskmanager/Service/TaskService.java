package com.example.taskmanager.Service;


import com.example.taskmanager.Dto.TaskDTO;
import com.example.taskmanager.Dto.TaskMapper;
import com.example.taskmanager.Entity.BoardColumn;
import com.example.taskmanager.Entity.Task;
import com.example.taskmanager.Entity.User;
import com.example.taskmanager.Repository.BoardColumnRepository;
import com.example.taskmanager.Repository.TaskRepository;
import com.example.taskmanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserService userService;

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        User user = userService.getAuthenticatedUser();

        // Gelen boardColumnName değerini kullanıyoruz, null ise "To Do" atanıyor
        String columnName = taskDTO.boardColumnName() != null ? taskDTO.boardColumnName() : "To Do";

        BoardColumn column = boardColumnRepository
                .findByNameAndUser(columnName, user)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found: " + columnName));

        task.setBoardColumn(column);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> getTasksForAuthenticatedUser() {
        User user = userService.getAuthenticatedUser(); // Bu metodu senin UserService zaten sağlıyor

        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream()
                .map(taskMapper::toDTO) // Her task'i DTO'ya dönüştürdük
                .collect(Collectors.toList());
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

                    // ✅ BoardColumn adı ile entity’yi bul ve set et
                    BoardColumn column = boardColumnRepository
                            .findByNameAndUser(taskDTO.boardColumnName(), existingTask.getUser())
                            .orElseThrow(() -> new ResourceNotFoundException("BoardColumn not found"));

                    updatedTask.setBoardColumn(column);
                    updatedTask.setUser(existingTask.getUser()); // Kullanıcıyı da taşı


                    updatedTask.setCreatedAt(existingTask.getCreatedAt());


                    updatedTask.setId(existingTask.getId());

                    Task savedTask = taskRepository.save(updatedTask);


                    return taskMapper.toDTO(savedTask);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }


    public Task updateTaskColumn(Long taskId, String boardColumnName) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        User user = task.getUser(); // Task'in kullanıcısını alıyoruz
        BoardColumn newColumn = boardColumnRepository.findByNameAndUser(boardColumnName, user)
                .orElseThrow(() -> new ResourceNotFoundException("BoardColumn not found with name: " + boardColumnName));

        task.setBoardColumn(newColumn); // Sütun güncelleniyor

        // Eğer yeni sütun "Done" ise, dueDate'i şu anki zaman ile güncelle
        if ("Done".equalsIgnoreCase(newColumn.getName())) {
            task.setDueDate(LocalDate.now());
        }
        return taskRepository.save(task); // Güncellenmiş task kaydediliyor
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
