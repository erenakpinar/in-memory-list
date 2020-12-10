package com.example.inmemorylist.controller;

import com.example.inmemorylist.model.document.Category;
import com.example.inmemorylist.model.request.AddCategoryRequest;
import com.example.inmemorylist.model.request.UpdateCategoryRequest;
import com.example.inmemorylist.properties.CategoryListProperties;
import com.example.inmemorylist.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryListProperties categoryListProperties;
    private final CategoryRepository repository;

    @GetMapping
    public ResponseEntity<Map<Long, String>> getCategories() {
        Map<Long, String> categoryList = categoryListProperties.getCategoryList();

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    public ResponseEntity<Category> add(@RequestBody AddCategoryRequest request) {
        Category category = repository.save(Category.builder().id(request.getCategoryId()).name(request.getName()).build());
        return ResponseEntity.ok(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(@PathVariable() Long id, @RequestBody UpdateCategoryRequest request) {
        Category category = repository.save(Category.builder().id(id).name(request.getName()).build());

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable() Long id) {
        repository.deleteById(id);

        return ResponseEntity.ok(true);
    }
}
