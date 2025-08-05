package com.orcafacil.api.infrastructure.persistence.jpa.company;

import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import com.orcafacil.api.infrastructure.persistence.entity.company.CompanyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataCompanyRepository extends JpaRepository<CompanyEntity, Integer> {
    Optional<CompanyEntity> findById(Integer userId);
    List<CompanyEntity> findAll();
    List<CompanyEntity> findByLegalName(String name);
    Optional<CompanyEntity> findByCnpj(String cnpj);
    @Modifying
    @Transactional
    @Query("""
    UPDATE CompanyEntity c
    SET c.legalName = :legalName,
        c.cnpj = :cnpj,
        c.address = :address,
        c.createdAt = :createdAt
    WHERE c.id = :id
""")
    void updateCompanyById(
            @Param("id") Integer id,
            @Param("legalName") String legalName,
            @Param("cnpj") String cnpj,
            @Param("address") AddressEntity address,
            @Param("createdAt") Date createdAt
    );

}