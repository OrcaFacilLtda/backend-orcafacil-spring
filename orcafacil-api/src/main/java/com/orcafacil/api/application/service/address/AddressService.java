package com.orcafacil.api.application.service.address;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.address.AddressRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;
import com.orcafacil.api.interfaceadapter.request.address.AddressRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final Validator validator;

    public AddressService(AddressRepository addressRepository, Validator validator) {
        this.addressRepository = addressRepository;
        this.validator = validator;
    }

    public Address createAddress(AddressRequest request) {
        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do endereço inválidos", violations);
        }

        Address address = AddressMapper.fromRequest(request, null);
        return addressRepository.save(address);
    }

    public Address getAddressById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Optional<Address> optAddress = addressRepository.findById(id);
        if (optAddress.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado");
        }

        return optAddress.get();
    }

    public Optional<Address> getAddressByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido.");
        }

        return addressRepository.findByUserId(userId);
    }

    public Optional<Address> getAddressByProviderId(Integer providerId) {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("ID de fornecedor inválido.");
        }
        return addressRepository.findByProviderId(providerId);
    }

    public Address updateAddress(Integer id, AddressRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do endereço inválidos", violations);
        }

        if (!addressRepository.findById(id).isPresent()) {
            throw new RuntimeException("Endereço não encontrado");
        }

        Address updatedAddress = AddressMapper.fromRequest(request, id);
        return addressRepository.update(updatedAddress);
    }

    public void deleteAddress(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        addressRepository.deleteById(id);
    }
}