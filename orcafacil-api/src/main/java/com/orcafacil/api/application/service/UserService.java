package com.orcafacil.api.application.service;

import com.orcafacil.api.domain.user.*;
import com.orcafacil.api.interfaceadapter.request.user.CreateUserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UpdateUserResquest;

import com.orcafacil.api.interfaceadapter.response.UserResponse;
import com.orcafacil.api.domain.exception.DomainException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User(
                null,
                request.getName(),
                request.getPhone(),
                request.getCpf(),
                UserType.valueOf(request.getUserType()),
                request.getBirthDate(),
                UserStatus.valueOf(request.getStatus()),
                request.getAddressId()
        );

        userRepository.save(user);

        return toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Integer id, UpdateUserResquest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new DomainException("Usuário não encontrado"));

        user.changeName(request.getName());
        user.changePhone(request.getPhone());
        user.changeCpf(request.getCpf());
        user.changeUserType(UserType.valueOf(request.getUserType()));
        user.changeBirthDate(request.getBirthDate());
        user.changeStatus(UserStatus.valueOf(request.getStatus()));
        user.changeAddressId(request.getAddressId());

        userRepository.update(user);

        return toResponse(user);
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DomainException("Usuário não encontrado"));
        return toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setPhone(user.getPhone());
        response.setCpf(user.getCpf());
        response.setUserType(user.getUserType().name());
        response.setBirthDate(user.getBirthDate());
        response.setStatus(user.getStatus().name());
        response.setAddressId(user.getAddressId());
        return response;
    }
}
