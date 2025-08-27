package com.orcafacil.api.infrastructure.persistence.entity.company;

import com.orcafacil.api.infrastructure.persistence.entity.address.AddressEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "legal_name", nullable = false, length = 150)
    private String legalName;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private AddressEntity address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    public CompanyEntity() {}

    public CompanyEntity(Integer id, String legalName, String cnpj, AddressEntity address, Date createdAt) {
        this.id = id;
        this.legalName = legalName;
        this.cnpj = cnpj;
        this.address = address;
        this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : new Date();
    }

    // === Getters and Setters ===

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt != null ? new Date(createdAt.getTime()) : null;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : null;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", legalName='" + legalName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", addressId=" + (address != null ? address.getId() : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
