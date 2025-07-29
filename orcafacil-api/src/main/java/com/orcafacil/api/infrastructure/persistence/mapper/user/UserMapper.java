package com.orcafacil.api.infrastructure.persistence.mapper.user;

import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;

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
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getCpf(),
                entity.getUserType(),
                entity.getBirthDate(),
                entity.getStatus(),
                AddressMapper.toDomain(entity.getAddress())
        );
    }
}
