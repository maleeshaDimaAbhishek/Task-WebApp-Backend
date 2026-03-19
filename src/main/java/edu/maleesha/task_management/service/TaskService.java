package edu.maleesha.task_management.service;

import edu.maleesha.task_management.model.DTO.TaskRequestDTO;
import edu.maleesha.task_management.model.DTO.TaskResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface TaskService {
    @Nullable List<TaskResponseDTO> getAllTask();

    @Nullable TaskResponseDTO getTaskById(String id);

    TaskResponseDTO createTask(@Valid TaskRequestDTO taskRequestDTO);
}
