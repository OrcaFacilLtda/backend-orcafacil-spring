package com.orcafacil.api.domain.provider;

import java.util.Optional;

public interface ProviderRepository {
    Provider save(Provider provider);
    Optional<Provider> findById(Integer id);
    Optional<Provider> findByCompanyId(Integer companyId);
    void deleteById(Integer id);
}