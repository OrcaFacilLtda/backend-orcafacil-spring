package com.orcafacil.api.domain.address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);
    Optional<Address> findById(Integer id);
    Optional<Address> findByUserId(Integer userId);
    Optional<Address> findByProviderId(Integer providerId);
    void deleteById(Integer id);

    Address update(Address address);


}