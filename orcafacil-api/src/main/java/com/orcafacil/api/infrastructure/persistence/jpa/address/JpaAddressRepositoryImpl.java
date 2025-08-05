package com.orcafacil.api.infrastructure.persistence.jpa.address;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.address.AddressRepository;
import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaAddressRepositoryImpl implements AddressRepository {

    private final SpringDataAddressRepository springDataAddressRepository;

    public JpaAddressRepositoryImpl(SpringDataAddressRepository springDataAddressRepository) {
        this.springDataAddressRepository = springDataAddressRepository;
    }

    @Override
    public Address save(Address address) {
        AddressEntity entity = AddressMapper.toEntity(address);
        return AddressMapper.toDomain(springDataAddressRepository.save(entity));
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return springDataAddressRepository.findById(id)
                .map(AddressMapper::toDomain);
    }

    @Override
    public Optional<Address> findByUserId(Integer userId) {
        AddressEntity entity = springDataAddressRepository.findByUserId(userId);
        return Optional.ofNullable(AddressMapper.toDomain(entity));
    }

    @Override
    public Optional<Address> findByProviderId(Integer providerId) {
        AddressEntity entity = springDataAddressRepository.findByProviderId(providerId);
        return Optional.ofNullable(AddressMapper.toDomain(entity));
    }

    @Override
    public void deleteById(Integer id) {
        springDataAddressRepository.deleteById(id);
    }

    @Override
    public Address update(Address address) {
        AddressEntity entity = AddressMapper.toEntity(address);
        return AddressMapper.toDomain(springDataAddressRepository.save(entity));
    }
}