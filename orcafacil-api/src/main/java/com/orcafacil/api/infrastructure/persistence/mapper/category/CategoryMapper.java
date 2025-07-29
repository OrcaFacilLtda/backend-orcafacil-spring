package com.orcafacil.api.infrastructure.persistence.mapper.category;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;

public class CategoryMapper {


    public static CategoryEntity toEntity(Category category){
        return  new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toDomain(CategoryEntity entity){
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
