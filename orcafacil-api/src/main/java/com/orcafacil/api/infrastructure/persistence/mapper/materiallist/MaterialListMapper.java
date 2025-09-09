package com.orcafacil.api.infrastructure.persistence.mapper.materiallist;

import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.infrastructure.persistence.entity.materiallist.MaterialListEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import org.springframework.stereotype.Component;

@Component
public class MaterialListMapper {

    private final ServiceMapper serviceMapper;

    public MaterialListMapper(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    public MaterialListEntity toEntity(MaterialList domain) {
        if (domain == null) return null;
        return new MaterialListEntity(
                domain.getId(),
                serviceMapper.toEntity(domain.getService()),
                domain.getNomeMaterial(),
                domain.getQuantity(),
                domain.getUnitPrice()
        );
    }

    public MaterialList toDomain(MaterialListEntity entity) {
        if (entity == null) return null;
        return new MaterialList(
                entity.getId(),
                serviceMapper.toDomain(entity.getService()),
                entity.getNomeMaterial(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }
}