package com.orcafacil.api.infrastructure.persistence.jpa.address;

import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataAddressRepository extends JpaRepository<AddressEntity, Integer> {

    @Query(value = "SELECT a.* FROM address a " +
            "JOIN user_account u ON u.address_id = a.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    AddressEntity findByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT a.* FROM address a " +
            "JOIN company c ON c.address_id = a.id " +
            "WHERE c.id = :providerId", nativeQuery = true)
    AddressEntity findByProviderId(@Param("providerId") Integer providerId);

    @Modifying
    @Transactional
    @Query("""
    UPDATE AddressEntity a SET 
        a.street = :street,
        a.number = :number,
        a.complement = :complement,
        a.neighborhood = :neighborhood,
        a.city = :city,
        a.state = :state,
        a.zipCode = :zipCode
    WHERE a.id = :id
""")
    void updateAddressById(
            Integer id,
            String street,
            String number,
            String complement,
            String neighborhood,
            String city,
            String state,
            String zipCode
    );

}