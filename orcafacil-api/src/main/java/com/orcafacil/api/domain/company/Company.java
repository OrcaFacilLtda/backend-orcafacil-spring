package com.orcafacil.api.domain.company;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.exception.DomainException;

import java.util.Date;
import java.util.Objects;

public class Company {

    private final Integer id;
    private final String legalName;
    private final String cnpj;
    private final Address address;
    private final Date createdAt;

    public Company(Integer id, String legalName, String cnpj, Address address, Date createdAt) {
        validateLegalName(legalName);
        validateCnpj(cnpj);
        validateAddress(address);

        this.id = id;
        this.legalName = legalName;
        this.cnpj = cnpj;
        this.address = address;
        this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : new Date();
    }

    private void validateLegalName(String legalName) {
        if (isNullOrEmpty(legalName)) {
            throw new DomainException("Razão social é obrigatória.");
        }
    }

    private void validateCnpj(String cnpj) {
        if (isNullOrEmpty(cnpj) || !cnpj.matches("\\d{14}")) {
            throw new DomainException("CNPJ inválido. Deve conter exatamente 14 dígitos numéricos.");
        }
    }

    private void validateAddress(Address address) {
        if (address == null) {
            throw new DomainException("Endereço da empresa é obrigatório.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }



    public Integer getId() {
        return id;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Address getAddress() {
        return address;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    // Métodos withX
    public Company withId(Integer newId) {
        return new Company(newId, legalName, cnpj, address, createdAt);
    }

    public Company withLegalName(String newLegalName) {
        return new Company(id, newLegalName, cnpj, address, createdAt);
    }

    public Company withCnpj(String newCnpj) {
        return new Company(id, legalName, newCnpj, address, createdAt);
    }

    public Company withAddress(Address newAddress) {
        return new Company(id, legalName, cnpj, newAddress, createdAt);
    }

    public Company withCreatedAt(Date newCreatedAt) {
        return new Company(id, legalName, cnpj, address, newCreatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(cnpj, company.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }

    @Override
    public String toString() {
        return "Company{" +
                "legalName='" + legalName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
