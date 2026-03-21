package edu.maleesha.task_management.service.impl;

import edu.maleesha.task_management.exception.ResourceNotFoundException;
import edu.maleesha.task_management.model.DTO.TaskRequestDTO;
import edu.maleesha.task_management.model.DTO.TaskResponseDTO;
import edu.maleesha.task_management.model.TaskStatus;
import edu.maleesha.task_management.model.entity.Category;
import edu.maleesha.task_management.model.entity.Task;
import edu.maleesha.task_management.model.entity.User;
import edu.maleesha.task_management.repository.CategoryRepository;
import edu.maleesha.task_management.repository.TaskRepository;
import edu.maleesha.task_management.repository.UserRepository;
import edu.maleesha.task_management.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public List<TaskResponseDTO> getAllTask() {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUser_Id(currentUser.getId());
        return tasks.stream()
                .map(this::toTaskResponseDTO)
                .toList();
    }

    @Override
    public List<TaskResponseDTO> getAllTaskForAdmin() {
        User currentUser = getCurrentUser();
        if (!isAdmin(currentUser)) {
            throw new AccessDeniedException("Admin access required");
        }
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::toTaskResponseDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        User currentUser = getCurrentUser();
        Task task=taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        ensureCanAccessTask(task, currentUser);
        return toTaskResponseDTO(task);
    }
    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        ensureCanAccessTask(task, currentUser);

        if(taskRequestDTO.getTitle()!=null && !taskRequestDTO.getTitle().isBlank()) {
            task.setTitle(taskRequestDTO.getTitle().trim());
        }
        if (taskRequestDTO.getDescription()!=null && !taskRequestDTO.getDescription().isBlank()) {
            task.setDescription(taskRequestDTO.getDescription().trim());
        }
        if (taskRequestDTO.getStatus()!=null && !taskRequestDTO.getStatus().isBlank()) {
            task.setStatus(TaskStatus.valueOf(taskRequestDTO.getStatus().trim().toUpperCase()));
        }
        if (taskRequestDTO.getCategoryId() != null) {
            Category category=categoryRepository.findById(taskRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + taskRequestDTO.getCategoryId()));
            task.setCategory(category);
        }
        return toTaskResponseDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        ensureCanAccessTask(task, currentUser);
        taskRepository.delete(task);
    }
    
    @Transactional
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(dto.getStatus().toUpperCase()));
        } else {
            task.setStatus(TaskStatus.TODO);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with ID: " + dto.getCategoryId()));
            task.setCategory(category);
        }

        // get logged-in username from JWT
        User user = getCurrentUser();

        task.setUser(user);

        Task saved = taskRepository.save(task);

        return toTaskResponseDTO(saved);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null || "anonymousUser".equals(authentication.getName())) {
            throw new ResourceNotFoundException("User not found");
        }
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private boolean isAdmin(User user) {
        return user.getStatus() != null && "ADMIN".equalsIgnoreCase(user.getStatus());
    }

    private void ensureCanAccessTask(Task task, User currentUser) {
        if (isAdmin(currentUser)) {
            return;
        }
        if (task.getUser() == null || !task.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Task not found with ID: " + task.getId());
        }
    }

    private TaskResponseDTO toTaskResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus() != null ? task.getStatus().name() : null);
        dto.setCreatedAt(task.getCreated());
        dto.setCategoryName(task.getCategory() != null ? task.getCategory().getName() : null);
        dto.setUserName(task.getUser() != null ? task.getUser().getUsername() : null);
        return dto;
    }
}
