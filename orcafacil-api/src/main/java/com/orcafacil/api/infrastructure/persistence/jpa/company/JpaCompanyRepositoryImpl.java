package com.orcafacil.api.infrastructure.persistence.jpa.company;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.category.CategoryRepository;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCompanyRepositoryImpl implements CompanyRepository {

    private final SpringDataCompanyRepository springDataCategoryRepository;

    public JpaCompanyRepositoryImpl(SpringDataCompanyRepository springDataCategoryRepository) {
        this.springDataCategoryRepository = springDataCategoryRepository;
    }


    @Override
    public Company save(Company company) {
        return null;
    }

    @Override
    public Company update(Company company) {
        return null;
    }

    @Override
    public void deleteByUserId(Integer id) {

    }
}