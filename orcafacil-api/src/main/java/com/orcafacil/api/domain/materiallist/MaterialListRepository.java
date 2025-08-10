package com.orcafacil.api.domain.materiallist;

import java.util.List;

public interface MaterialListRepository {
    List<MaterialList> findByServiceId(Integer serviceId);
}
