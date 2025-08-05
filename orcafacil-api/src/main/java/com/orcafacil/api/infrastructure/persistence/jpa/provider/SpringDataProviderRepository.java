package com.orcafacil.api.infrastructure.persistence.jpa.provider;

import com.orcafacil.api.infrastructure.persistence.entity.provider.ProviderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataProviderRepository extends JpaRepository<ProviderEntity, Integer> {
    Optional<ProviderEntity> findByCompanyId(Integer companyId);

    @Modifying
    @Transactional
    @Query("UPDATE ProviderEntity p SET p.category.id = :newCategoryId WHERE p.id = :userId")
    int updateCategoryByUserId(Integer userId, Integer newCategoryId);

    @Override
    boolean existsById(Integer integer);
}

