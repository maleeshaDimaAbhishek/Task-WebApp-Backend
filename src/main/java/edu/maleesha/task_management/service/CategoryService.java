package edu.maleesha.task_management.service;

import edu.maleesha.task_management.model.DTO.CategoryDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CategoryService {
    @Nullable List<CategoryDTO> getAllCategories();

    @Nullable CategoryDTO getCategoryById(Long id);

    @Nullable CategoryDTO createCategory(@Valid CategoryDTO categoryDTO);
}
