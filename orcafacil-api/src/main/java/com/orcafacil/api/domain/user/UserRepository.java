package com.orcafacil.api.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    void deleteById(Integer id);
    User update(User user);
    Optional<User> findById(Integer id);
    List<User> findUserByType(String type);
    List<User> findByStatus(UserStatus status);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    List<User> findByTypeAndStatus(UserType type, UserStatus status);

}
