package com.orcafacil.api.infrastructure.persistence.jpa.company;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.category.CategoryRepository;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import com.orcafacil.api.infrastructure.persistence.entity.company.CompanyEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.category.CategoryMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.company.CompanyMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCompanyRepositoryImpl implements CompanyRepository {

    private final SpringDataCompanyRepository springDataCompanyRepository;

    public JpaCompanyRepositoryImpl(SpringDataCompanyRepository springDataCompanyRepository) {
        this.springDataCompanyRepository = springDataCompanyRepository;
    }


    @Override
    public Company save(Company company) {
        CompanyEntity entity= CompanyMapper.toEntity(company);
         springDataCompanyRepository.save(entity);
         return CompanyMapper.toDomain(entity);
    }

    @Override
    public Company update(Company company) {
        CompanyEntity entity= CompanyMapper.toEntity(company);
        springDataCompanyRepository.updateCompanyById(entity.getId(),entity.getLegalName(),entity.getCnpj(),entity.getAddress(),entity.getCreatedAt());
        return CompanyMapper.toDomain(entity);
    }

    @Override
    public void deleteByCompanyId(Integer id) {
        springDataCompanyRepository.deleteById(id);
    }

    @Override
    public Optional<Company> getByUserId(Integer id) {
        return springDataCompanyRepository.findById(id)
                .map(CompanyMapper::toDomain);
    }

    @Override
    public Optional<Company> findByCnpj(String cnpj) {
        return springDataCompanyRepository
                .findByCnpj(cnpj)
                .map(CompanyMapper::toDomain);
    }




}