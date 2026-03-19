package edu.maleesha.task_management.service;

import edu.maleesha.task_management.model.DTO.CategoryDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CategoryService {
    @Nullable List<CategoryDTO> getAllCategories();
}
