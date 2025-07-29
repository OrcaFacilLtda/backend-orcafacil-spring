package com.orcafacil.api.infrastructure.persistence.mapper.provider;

import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.infrastructure.persistence.entity.provider.ProviderEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.category.CategoryMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.company.CompanyMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;

public class ProviderMapper {

    public static ProviderEntity toEntity(Provider provider) {
        return new ProviderEntity(
                UserMapper.toEntity(provider.getUser()),
                CompanyMapper.toEntity(provider.getCompany()),
                CategoryMapper.toEntity(provider.getCategory())
        );
    }

    public static Provider toDomain(ProviderEntity entity) {
        return new Provider(
                UserMapper.toDomain(entity.getUser()),
                CompanyMapper.toDomain(entity.getCompany()),
                CategoryMapper.toDomain(entity.getCategory())
        );
    }
}