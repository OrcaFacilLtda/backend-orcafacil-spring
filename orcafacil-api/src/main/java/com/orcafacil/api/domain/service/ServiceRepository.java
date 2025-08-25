package com.orcafacil.api.domain.service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {

    Service save(Service service);

    Optional<Service> findById(Integer id);

    List<Service> findAll();

    void deleteById(Integer id);

    List<Service> findByUserId(Integer userId);


}
