package edu.maleesha.task_management.model.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    private String categoryName;
    private String userName;
}
