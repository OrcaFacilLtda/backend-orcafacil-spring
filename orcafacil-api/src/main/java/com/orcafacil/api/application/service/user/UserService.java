package com.orcafacil.api.application.service.user;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.service.ServiceRepository; // IMPORTADO
import com.orcafacil.api.domain.service.ServiceStatus;   // IMPORTADO
import com.orcafacil.api.domain.user.*;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final ServiceRepository serviceRepository;


    public UserService(UserRepository userRepository,
                       AddressService addressService,
                       Validator validator,
                       PasswordEncoder passwordEncoder,
                       ServiceRepository serviceRepository) {

        this.userRepository = userRepository;
        this.addressService = addressService;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public User create(UserRequest request) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados do usuário inválidos", violations);
        }

        if (request.getAddress() == null) {
            throw new IllegalArgumentException("Endereço é obrigatório para criar usuário.");
        }

        Address address = new Address(
                null,
                request.getAddress().getZipCode(),
                request.getAddress().getStreet(),
                request.getAddress().getNumber(),
                request.getAddress().getNeighborhood(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getComplement()
        );

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                null,
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                hashedPassword,
                request.getCpf(),
                UserType.valueOf(request.getUserType()),
                request.getBirthDate(),
                UserStatus.valueOf(request.getStatus()),
                address
        );

        return userRepository.save(user);
    }

    @Transactional
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

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            existingUser = existingUser.withName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            existingUser = existingUser.withPhone(request.getPhone());
        }
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            existingUser = existingUser.withEmail(request.getEmail());
        }
        if (request.getCpf() != null && request.getCpf().matches("\\d{11}")) {
            existingUser = existingUser.withCpf(request.getCpf());
        }
        if (request.getUserType() != null && !request.getUserType().trim().isEmpty()) {
            existingUser = existingUser.withUserType(UserType.valueOf(request.getUserType()));
        }
        if (request.getBirthDate() != null) {
            existingUser = existingUser.withBirthDate(request.getBirthDate());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            existingUser = existingUser.withStatus(UserStatus.valueOf(request.getStatus()));
        }

        if (request.getAddress() != null) {
            Address updatedAddress;
            if (request.getAddress().getId() != null) {
                updatedAddress = addressService.updateAddress(request.getAddress());
            } else {
                updatedAddress = addressService.createAddress(request.getAddress());
            }
            existingUser = existingUser.withAddress(updatedAddress);
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            if (request.getCurrentPassword() == null
                    || !passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPassword())) {
                throw new RuntimeException("Senha atual incorreta.");
            }
            existingUser = existingUser.withPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.update(existingUser);
    }

    @Transactional
    public User updateByAdmin(Integer id, UserUpdateRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        Set<ConstraintViolation<UserUpdateRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados de atualização inválidos", violations);
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            existingUser = existingUser.withName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            existingUser = existingUser.withPhone(request.getPhone());
        }
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            existingUser = existingUser.withEmail(request.getEmail());
        }
        if (request.getCpf() != null && request.getCpf().matches("\\d{11}")) {
            existingUser = existingUser.withCpf(request.getCpf());
        }
        if (request.getUserType() != null && !request.getUserType().trim().isEmpty()) {
            existingUser = existingUser.withUserType(UserType.valueOf(request.getUserType()));
        }
        if (request.getBirthDate() != null) {
            existingUser = existingUser.withBirthDate(request.getBirthDate());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            existingUser = existingUser.withStatus(UserStatus.valueOf(request.getStatus()));
        }

        if (request.getAddress() != null) {
            Address updatedAddress;
            if (request.getAddress().getId() != null) {
                updatedAddress = addressService.updateAddress(request.getAddress());
            } else {
                updatedAddress = addressService.createAddress(request.getAddress());
            }
            existingUser = existingUser.withAddress(updatedAddress);
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser = existingUser.withPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.update(existingUser);
    }


    @Transactional
    public void delete(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

         boolean hasActiveServices = serviceRepository.findByUserId(id).stream()
                .anyMatch(service ->
                        service.getServiceStatus() != ServiceStatus.COMPLETED &&
                                service.getServiceStatus() != ServiceStatus.REJECTED
                );

        if (hasActiveServices) {
            throw new IllegalStateException("Usuário não pode ser excluído, pois possui serviços em andamento.");
        }

            userRepository.deleteById(id);
    }



    public Optional<User> findById(Integer id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID inválido.");
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email não pode ser vazio.");
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) throw new IllegalArgumentException("CPF deve conter 11 dígitos.");
        return userRepository.findByCpf(cpf);
    }

    public List<User> findByType(String type) {
        if (type == null || type.isEmpty()) throw new IllegalArgumentException("Tipo de usuário inválido.");
        return userRepository.findUserByType(type);
    }

    public List<User> findByStatus(UserStatus status) {
        if (status == null) throw new IllegalArgumentException("Status não pode ser nulo.");
        return userRepository.findByStatus(status);
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email não pode ser vazio.");
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) throw new IllegalArgumentException("CPF inválido.");
        return userRepository.findByCpf(cpf).isPresent();
    }

    @Transactional(readOnly = true)
    public List<User> findByTypeAndStatus(UserType type, UserStatus status) {
        if (type == null || status == null) throw new IllegalArgumentException("Tipo e Status não podem ser nulos.");
        return userRepository.findByTypeAndStatus(type, status);
    }

    @Transactional(readOnly = true)
    public long countTotalUsers() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public long countActiveProviders() {
        return userRepository.countByTypeAndStatus(UserType.PROVIDER, UserStatus.APPROVED);
    }

    @Transactional
    public User updateStatus(Integer id, UserStatus newStatus) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID do usuário inválido.");
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return userRepository.update(existingUser.withStatus(newStatus));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}