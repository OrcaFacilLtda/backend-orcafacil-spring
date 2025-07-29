package com.orcafacil.api.infrastructure.persistence.jpa.address;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.address.AddressRepository;
import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAddressRepositoryImpl implements AddressRepository {

    private final SpringDataAddressRepository springDataAddressRepository;

    @Autowired
    public JpaAddressRepositoryImpl(SpringDataAddressRepository springDataAddressRepository) {
        this.springDataAddressRepository = springDataAddressRepository;
    }

    @Override
    public Address save(Address address) {
        AddressEntity entity = AddressMapper.toEntity(address);
        AddressEntity savedEntity = springDataAddressRepository.save(entity);
        return AddressMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return springDataAddressRepository.findById(id)
                .map(AddressMapper::toDomain);
    }

    @Override
    public List<Address> findByUserId(Integer userId) {
        return springDataAddressRepository
                .findByUserId(userId)
                .stream()
                .map(AddressMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Address> findByProviderId(Integer providerId) {
        return springDataAddressRepository.findByProviderId(providerId)
                .stream()
                .map(AddressMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        springDataAddressRepository.deleteById(id);
    }

    @Override
    public Address update(Address address) {
        AddressEntity entity = AddressMapper.toEntity(address);

        springDataAddressRepository.updateAddressById(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode()
        );

        return address;
    }

}