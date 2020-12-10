package com.example.inmemorylist.config;

import com.example.inmemorylist.model.document.Category;
import com.example.inmemorylist.properties.CategoryListProperties;
import com.example.inmemorylist.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class InMemoryConfiguration {

    @Bean
    public CategoryListProperties getCategoryListProperties(CategoryRepository repository) {
        CategoryListProperties categoryListProperties = new CategoryListProperties();
        categoryListProperties.setCategoryList(
                repository.findAll().parallelStream()
                        .collect(Collectors.toMap(Category::getId, Category::getName))
        );
        return categoryListProperties;
    }
}
