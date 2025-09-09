package com.orcafacil.api.domain.company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);
    Company update(Company company);
    void deleteByCompanyId(Integer id);
    Optional<Company> getByUserId(Integer id);
    Optional<Company> findByCnpj(String cnpj);
    List<Company> findByAll();
    Optional<Company> findById(Integer id);

}
