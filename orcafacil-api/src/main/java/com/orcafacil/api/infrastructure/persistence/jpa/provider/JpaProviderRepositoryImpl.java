package com.orcafacil.api.infrastructure.persistence.jpa.provider;

import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.provider.ProviderRepository;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.infrastructure.persistence.entity.provider.ProviderEntity;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.provider.ProviderMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaProviderRepositoryImpl implements ProviderRepository {

    private final SpringDataProviderRepository springDataProviderRepository;


    public JpaProviderRepositoryImpl(SpringDataProviderRepository springDataProviderRepository) {
        this.springDataProviderRepository = springDataProviderRepository;
    }

    @Override
    public Provider save(Provider provider) {
        ProviderEntity entity = ProviderMapper.toEntity(provider);
        ProviderEntity savedEntity = springDataProviderRepository.save(entity);
        return ProviderMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Provider> findById(Integer id) {
        return springDataProviderRepository.findById(id)
                .map(ProviderMapper::toDomain);
    }

    @Override
    public Optional<Provider> findByCompanyId(Integer companyId) {
        return springDataProviderRepository.findByCompanyId(companyId)
                .map(ProviderMapper::toDomain);
    }

    @Override
    public void deleteById(Integer id) {
        springDataProviderRepository.deleteById(id);
    }

    @Override
    public Provider update(Provider provider) {
        return null;
    }

    @Override
    public boolean existsById(Integer id) {
        return  springDataProviderRepository.existsById(id);
    }

    @Override
    public List<Provider> findAllByUserIn(List<User> users) {
        List<UserEntity> userEntities = users.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());

        return springDataProviderRepository.findByUserIn(userEntities)
                .stream()
                .map(ProviderMapper::toDomain)
                .collect(Collectors.toList());
    }
}