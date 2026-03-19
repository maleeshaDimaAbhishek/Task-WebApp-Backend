package edu.maleesha.task_management.model.entity;

import edu.maleesha.task_management.model.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100)
    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
