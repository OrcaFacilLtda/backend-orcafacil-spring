package com.orcafacil.api.infrastructure.persistence.jpa.materiallist;

import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.domain.materiallist.MaterialListRepository;
import com.orcafacil.api.infrastructure.persistence.entity.materiallist.MaterialListEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.materiallist.MaterialListMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMaterialListRepositoryImpl implements MaterialListRepository {

    private final SpringMaterialListRepository repository;
    private final MaterialListMapper mapper;

    public JpaMaterialListRepositoryImpl(SpringMaterialListRepository repository, MaterialListMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<MaterialList> saveAll(List<MaterialList> materialLists) {
        List<MaterialListEntity> entities =
                materialLists.stream().map(mapper::toEntity).collect(Collectors.toList());
        List<MaterialListEntity> savedEntities = repository.saveAll(entities);
        return savedEntities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MaterialList> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByServiceId(Integer serviceId) {
        repository.deleteByServiceId(serviceId);
    }
}