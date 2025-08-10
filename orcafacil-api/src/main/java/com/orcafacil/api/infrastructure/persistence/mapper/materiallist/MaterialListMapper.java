package com.orcafacil.api.infrastructure.persistence.mapper.materiallist;

import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.infrastructure.persistence.entity.materiallist.MaterialListEntity;

public class MaterialListMapper {

    public static MaterialListEntity toEntity(MaterialList domain) {
        if (domain == null) return null;
        return new MaterialListEntity(
                domain.getId(),
                domain.getService(),
                domain.getNomeMaterial(),
                domain.getQuantity(),
                domain.getUnitPrice()
        );
    }

    public static MaterialList toDomain(MaterialListEntity entity) {
        if (entity == null) return null;
        return new MaterialList(
                entity.getId(),
                entity.getService(),
                entity.getNomeMaterial(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }
}
