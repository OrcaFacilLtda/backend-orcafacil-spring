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

    public Address updateAddress(AddressRequest request) {
        if (request.getId() == null || request.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        // Validação do Bean Validation no request
        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do endereço inválidos", violations);
        }

        Address existingAddress = addressRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        Address updatedAddress = existingAddress
                .withStreet(request.getStreet())
                .withNumber(request.getNumber())
                .withComplement(request.getComplement())
                .withNeighborhood(request.getNeighborhood())
                .withCity(request.getCity())
                .withState(request.getState())
                .withZipCode(request.getZipCode());

        validateAddress(updatedAddress);

        return addressRepository.update(updatedAddress);
    }

    private void validateAddress(Address address) {
        if (address.getZipCode() == null || !address.getZipCode().matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 dígitos numéricos.");
        }
        if (address.getStreet() == null || address.getStreet().trim().isEmpty()) {
            throw new IllegalArgumentException("Rua não pode ser vazia.");
        }
        if (address.getNumber() == null || address.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Número do endereço é obrigatório.");
        }
        if (address.getNeighborhood() == null || address.getNeighborhood().trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro é obrigatório.");
        }
        if (address.getCity() == null || address.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória.");
        }
        if (address.getState() == null || address.getState().trim().length() != 2) {
            throw new IllegalArgumentException("Estado deve conter a sigla de 2 letras.");
        }
        if (address.getComplement() != null && address.getComplement().length() > 50) {
            throw new IllegalArgumentException("Complemento não pode ter mais que 50 caracteres.");
        }
    }


    public void deleteAddress(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        addressRepository.deleteById(id);
    }
}