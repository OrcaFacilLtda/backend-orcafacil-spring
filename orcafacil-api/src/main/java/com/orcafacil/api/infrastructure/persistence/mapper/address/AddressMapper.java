package com.orcafacil.api.infrastructure.persistence.mapper.address;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import com.orcafacil.api.interfaceadapter.request.address.AddressRequest;

public class AddressMapper {

    public static AddressEntity toEntity(Address address) {
        return new AddressEntity(
                address.getId(),
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getComplement()
        );
    }

    public static Address toDomain(AddressEntity entity) {
        return new Address(
                entity.getId(),
                entity.getZipCode(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getComplement()
        );
    }

    public static Address fromRequest(AddressRequest request, Integer id) {
        return new Address(
                id,
                request.getZipCode(),
                request.getStreet(),
                request.getNumber(),
                request.getNeighborhood(),
                request.getCity(),
                request.getState(),
                request.getComplement()
        );


    }

}