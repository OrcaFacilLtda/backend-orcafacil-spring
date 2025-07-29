package com.orcafacil.api.infrastructure.persistence.jpa.provider;

import com.orcafacil.api.infrastructure.persistence.entity.provider.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataProviderRepository extends JpaRepository<ProviderEntity, Integer> {
    Optional<ProviderEntity> findByCompanyId(Integer companyId);
}