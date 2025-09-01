package com.orcafacil.api.domain.materiallist;

import java.util.List;

public interface MaterialListRepository {
    List<MaterialList> saveAll(List<MaterialList> materialLists);
    List<MaterialList> findByServiceId(Integer serviceId);
    void deleteByServiceId(Integer serviceId);

}