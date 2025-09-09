package com.orcafacil.api.infrastructure.persistence.mapper.user;

import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getUserType(),
                user.getBirthDate(),
                user.getCpf(),
                user.getStatus(),
                AddressMapper.toEntity(user.getAddress())
        );
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCpf(),
                entity.getUserType(),
                entity.getBirthDate(),
                entity.getStatus(),
                AddressMapper.toDomain(entity.getAddress())
        );
    }

    public static User toDomain(UserRequest request) {
        return new User(
                null,
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword(),
                request.getCpf(),
                UserType.valueOf(request.getUserType().toUpperCase()),
                request.getBirthDate(),
                UserStatus.valueOf(request.getStatus().toUpperCase()),
                AddressMapper.fromRequest(request.getAddress(), request.getAddress().getId())
        );
    }
}
