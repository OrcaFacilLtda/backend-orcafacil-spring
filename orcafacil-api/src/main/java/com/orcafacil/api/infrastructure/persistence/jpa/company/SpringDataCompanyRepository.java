package com.orcafacil.api.infrastructure.persistence.jpa.company;

import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataCompanyRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findById(Integer userId);
    List<CategoryEntity> findAll();
    List<CategoryEntity> findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET c.name = :name, c.description = :description WHERE c.id = :id")
    void updateCategoryById(Integer id, String name, String description);
}