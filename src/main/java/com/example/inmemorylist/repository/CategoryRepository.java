package com.example.inmemorylist.repository;

import com.example.inmemorylist.model.document.Category;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("#{#n1ql.selectEntity}")
    List<Category> findAll();
}
