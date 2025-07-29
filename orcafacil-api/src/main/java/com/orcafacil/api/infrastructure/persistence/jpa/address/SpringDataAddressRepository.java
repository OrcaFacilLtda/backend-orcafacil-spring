package com.orcafacil.api.infrastructure.persistence.jpa.address;

import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataAddressRepository extends JpaRepository<AddressEntity, Integer> {
    List<AddressEntity> findByUserId(Integer userId);
    List<AddressEntity> findByProviderId(Integer providerId);

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