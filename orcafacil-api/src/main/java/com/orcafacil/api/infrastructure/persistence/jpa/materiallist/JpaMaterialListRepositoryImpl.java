package com.orcafacil.api.infrastructure.persistence.jpa.materiallist;

import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.domain.materiallist.MaterialListRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.materiallist.MaterialListMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMaterialListRepositoryImpl implements MaterialListRepository {

    private final SpringMaterialListRepository repository;

    public JpaMaterialListRepositoryImpl(SpringMaterialListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialList> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(MaterialListMapper::toDomain)
                .collect(Collectors.toList());
    }
}
