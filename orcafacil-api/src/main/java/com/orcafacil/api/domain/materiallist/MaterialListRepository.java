package com.orcafacil.api.domain.materiallist;

import java.util.List;

public interface MaterialListRepository {
    List<MaterialList> saveAll(List<MaterialList> materialLists); // <-- MÉTODO ADICIONADO
    List<MaterialList> findByServiceId(Integer serviceId);
}