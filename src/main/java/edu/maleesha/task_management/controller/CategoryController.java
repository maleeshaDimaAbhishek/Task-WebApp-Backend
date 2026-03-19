package edu.maleesha.task_management.controller;

import edu.maleesha.task_management.model.DTO.CategoryDTO;
import edu.maleesha.task_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
     @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
         return ResponseEntity.ok(categoryService.getAllCategories());
     }

}
