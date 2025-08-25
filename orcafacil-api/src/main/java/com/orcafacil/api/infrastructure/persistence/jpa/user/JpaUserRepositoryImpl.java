package com.orcafacil.api.infrastructure.persistence.jpa.user;

import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaUserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Autowired
    public JpaUserRepositoryImpl(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        return UserMapper.toDomain(springDataUserRepository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        springDataUserRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        return UserMapper.toDomain(springDataUserRepository.save(entity));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return springDataUserRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findUserByType(String type) {
        return springDataUserRepository
                .findByUserType(UserType.valueOf(type.toUpperCase()))
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByStatus(UserStatus status) {
        return springDataUserRepository.findByStatus(status)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return springDataUserRepository
                .findByCpf(cpf)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findByTypeAndStatus(UserType type, UserStatus status) {
        return springDataUserRepository.findByUserTypeAndStatus(type, status)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }


    @Override
    public long count() {
        return springDataUserRepository.count();
    }

    @Override
    public long countByTypeAndStatus(UserType type, UserStatus status) {
        return springDataUserRepository.countByUserTypeAndStatus(type, status);
    }
}