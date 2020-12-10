package com.example.inmemorylist.properties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryListProperties {
    private final Map<Long, String> categoryList;

    public CategoryListProperties() {
        this.categoryList = new ConcurrentHashMap<>();
    }

    public Map<Long, String> getCategoryList() {
        return categoryList;
    }

    public void setCategory(Long categoryId, String name) {
        categoryList.put(categoryId, name);
    }

    public void setCategoryList(Map<Long, String> newCategoryList) {
        if (!categoryList.isEmpty()) {
            categoryList.clear();
        }
        categoryList.putAll(newCategoryList);
    }

    public void removeCategory(long categoryId) {
        this.categoryList.remove(categoryId);
    }
}
