package com.orcafacil.api.domain.company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);
    Company update(Company company);
    void deleteByUserId(Integer id);

}
