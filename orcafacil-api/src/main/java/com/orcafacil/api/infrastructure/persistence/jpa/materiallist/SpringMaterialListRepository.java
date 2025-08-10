package com.orcafacil.api.infrastructure.persistence.jpa.materiallist;

import com.orcafacil.api.infrastructure.persistence.entity.materiallist.MaterialListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringMaterialListRepository extends JpaRepository<MaterialListEntity, Integer> {
    List<MaterialListEntity> findByServiceId(Integer serviceId);
}
