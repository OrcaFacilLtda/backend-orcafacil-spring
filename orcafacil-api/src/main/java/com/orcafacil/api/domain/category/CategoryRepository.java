package com.orcafacil.api.domain.category;

import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Category update(Category category);

    Optional<Category> findById(Integer id);

    List<Category> findAll();
    List<Category> findByName(String name);
    boolean existsById(Integer id);

    void deleteById(Integer id);

}
