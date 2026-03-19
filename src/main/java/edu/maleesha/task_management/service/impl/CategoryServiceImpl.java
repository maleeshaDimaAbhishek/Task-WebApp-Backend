package edu.maleesha.task_management.service.impl;

import edu.maleesha.task_management.model.DTO.CategoryDTO;
import edu.maleesha.task_management.model.entity.Category;
import edu.maleesha.task_management.repository.CategoryRepository;
import edu.maleesha.task_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }
}
