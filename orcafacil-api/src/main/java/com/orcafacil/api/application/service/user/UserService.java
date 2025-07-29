package com.orcafacil.api.application.service.user;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.address.AddressRepository;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository,AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository= addressRepository;
    }


    public User create(UserRequest request) {

        Address addressDomain = new Address(
                null,
                request.getAddress().getZipCode(),
                request.getAddress().getStreet(),
                request.getAddress().getNumber(),
                request.getAddress().getNeighborhood(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getComplement()
        );

        Address savedAddress = addressRepository.save(addressDomain);

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
                request.getAddress()
        );
        return userRepository.save(user);
    }
    public User update(Integer id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getName() != null) {
            existingUser = existingUser.withName(request.getName());
        }

        if (request.getPhone() != null) {
            existingUser = existingUser.withPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            existingUser = existingUser.withEmail(request.getEmail());
        }

        if (request.getCpf() != null) {
            existingUser = existingUser.withCpf(request.getCpf());
        }

        if (request.getUserType() != null) {
            existingUser = existingUser.withUserType(UserType.valueOf(request.getUserType()));
        }

        if (request.getBirthDate() != null) {
            existingUser = existingUser.withBirthDate(request.getBirthDate());
        }

        if (request.getStatus() != null) {
            existingUser = existingUser.withStatus(UserStatus.valueOf(request.getStatus()));
        }

        if (request.getAddress() != null) {
            existingUser = existingUser.withAddress(request.getAddress());
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
        userRepository.delete(id);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public List<User> findByType(String type) {
        return userRepository.findUserByType(type);
    }

    public List<User> findByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }
}
