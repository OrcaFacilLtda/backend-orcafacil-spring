package com.orcafacil.api.infrastructure.persistence.jpa.user;

import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByCpf(String cpf);
    List<UserEntity> findByUserType(String userType);
    List<UserEntity> findByStatus(UserStatus status);


    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.name = :name, u.email = :email, u.password = :password, u.phone = :phone, " +
            "u.userType = :userType, u.birthDate = :birthDate, u.cpf = :cpf, u.status = :status, u.addressId = :addressId " +
            "WHERE u.id = :id")
    int updateUser(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("userType") UserType userType,
            @Param("birthDate") java.util.Date birthDate,
            @Param("cpf") String cpf,
            @Param("status") UserStatus status,
            @Param("addressId") Integer addressId
    );
}
