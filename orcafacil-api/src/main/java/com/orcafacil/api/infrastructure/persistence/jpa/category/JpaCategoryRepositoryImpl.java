package com.orcafacil.api.infrastructure.persistence.jpa.category;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.category.CategoryRepository;
import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCategoryRepositoryImpl implements CategoryRepository {

    private final SpringDataCategoryRepository springDataCategoryRepository;

    @Autowired
    public JpaCategoryRepositoryImpl(SpringDataCategoryRepository springDataCategoryRepository) {
        this.springDataCategoryRepository = springDataCategoryRepository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        CategoryEntity savedEntity = springDataCategoryRepository.save(entity);
        return CategoryMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return springDataCategoryRepository
                .findById(id)
                .map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return springDataCategoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findByName(String name) {
        return springDataCategoryRepository
                .findByName(name)
                .stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());

    }

    @Override
    public boolean existsById(Integer id) {
        return springDataCategoryRepository
                .existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        springDataCategoryRepository.deleteById(id);
    }

    @Override
    public Category update(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);

        springDataCategoryRepository.updateCategoryById(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return category;
    }

}