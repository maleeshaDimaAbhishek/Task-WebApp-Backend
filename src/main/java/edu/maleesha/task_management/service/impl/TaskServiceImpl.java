package edu.maleesha.task_management.service.impl;

import edu.maleesha.task_management.exception.ResourceNotFoundException;
import edu.maleesha.task_management.model.DTO.TaskRequestDTO;
import edu.maleesha.task_management.model.DTO.TaskResponseDTO;
import edu.maleesha.task_management.model.TaskStatus;
import edu.maleesha.task_management.model.entity.Category;
import edu.maleesha.task_management.model.entity.Task;
import edu.maleesha.task_management.repository.CategoryRepository;
import edu.maleesha.task_management.repository.TaskRepository;
import edu.maleesha.task_management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    @Override
    public List<TaskResponseDTO> getAllTask() {
        List<Task> tasks=taskRepository.findAll();
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .toList();
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Task task=taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = modelMapper.map(taskRequestDTO, Task.class);
        //Parse status string to the Enum
        if (taskRequestDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(taskRequestDTO.getStatus().toUpperCase()));
        }
        //Category Validation
        if (taskRequestDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + taskRequestDTO.getCategoryId()));
            task.setCategory(category);
        }
        //Save to Database
        Task savedTask = taskRepository.save(task);

        //Map to ResponseDTO
        return modelMapper.map(savedTask,TaskResponseDTO.class);
    }

    @Override
    public @Nullable TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        //Check Task Exists
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        if(taskRequestDTO.getTitle()!=null) {
            taskRequestDTO.setTitle(taskRequestDTO.getTitle());
        }
        if (taskRequestDTO.getDescription()!=null) {
            taskRequestDTO.setDescription(taskRequestDTO.getDescription());
        }
        Category category=categoryRepository.findById(taskRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + taskRequestDTO.getCategoryId()));
        task.setCategory(category);
        //Update task
        return modelMapper.map(taskRepository.save(task), TaskResponseDTO.class);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        taskRepository.delete(task);
    }

}
