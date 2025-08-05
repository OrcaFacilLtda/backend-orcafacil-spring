package com.orcafacil.api.application.service.user;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.user.*;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final Validator validator;

    public UserService(UserRepository userRepository, AddressService addressService, Validator validator) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.validator = validator;
    }

    public User create(UserRequest request) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do usuário inválidos", violations);
        }

        Address savedAddress = addressService.createAddress(request.getAddress());

        User user = new User(
                null,
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword(),
                request.getCpf(),
                UserType.valueOf(request.getUserType()),
                request.getBirthDate(),
                UserStatus.valueOf(request.getStatus()),
                savedAddress
        );

        return userRepository.save(user);
    }

    public User create(UserRequest request, Address address) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do usuário inválidos", violations);
        }

        Address userAddress = address != null ? address : addressService.createAddress(request.getAddress());

        User user = new User(
                null,
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword(),
                request.getCpf(),
                UserType.valueOf(request.getUserType()),
                request.getBirthDate(),
                UserStatus.valueOf(request.getStatus()),
                userAddress
        );

        return userRepository.save(user);
    }

    public User update(Integer id, UserUpdateRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        Set<ConstraintViolation<UserUpdateRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados de atualização inválidos", violations);
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getName() != null) existingUser = existingUser.withName(request.getName());
        if (request.getPhone() != null) existingUser = existingUser.withPhone(request.getPhone());
        if (request.getEmail() != null) existingUser = existingUser.withEmail(request.getEmail());
        if (request.getCpf() != null) existingUser = existingUser.withCpf(request.getCpf());
        if (request.getUserType() != null) existingUser = existingUser.withUserType(UserType.valueOf(request.getUserType()));
        if (request.getBirthDate() != null) existingUser = existingUser.withBirthDate(request.getBirthDate());
        if (request.getStatus() != null) existingUser = existingUser.withStatus(UserStatus.valueOf(request.getStatus()));

        if (request.getAddress() != null && request.getAddress().getId() != null) {
            Address updatedAddress = addressService.updateAddress(request.getAddress().getId(), request.getAddress());
            existingUser = existingUser.withAddress(updatedAddress);
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            if (request.getCurrentPassword() == null || !request.getCurrentPassword().equals(existingUser.getPassword())) {
                throw new RuntimeException("Senha atual incorreta.");
            }
            existingUser = existingUser.withPassword(request.getPassword());
        }

        return userRepository.update(existingUser);
    }

    public void delete(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            Address address = user.getAddress();
            if (address != null && address.getId() != null) {
                addressService.deleteAddress(address.getId());
            }
            userRepository.deleteById(id);
        });
    }

    public Optional<User> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
        return userRepository.findByCpf(cpf);
    }

    public List<User> findByType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Tipo de usuário inválido.");
        }
        return userRepository.findUserByType(type);
    }

    public List<User> findByStatus(UserStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo.");
        }
        return userRepository.findByStatus(status);
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
        return userRepository.findByCpf(cpf).isPresent();
    }
}