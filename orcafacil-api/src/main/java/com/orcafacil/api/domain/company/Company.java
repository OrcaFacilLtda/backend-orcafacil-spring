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
        validateAndressId(address);

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
        if (!isValidCnpj(cnpj)) {
            throw new DomainException("CNPJ inválido.");
        }
    }

    private void validateAndressId(Address andress) {
        if (address == null ) {
            throw new DomainException("Endereço da empresa é obrigatório.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    private boolean isValidCnpj(String cnpj) {
        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight1[i];
            }
            int mod = sum % 11;
            char check1 = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight2[i];
            }
            mod = sum % 11;
            char check2 = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            return cnpj.charAt(12) == check1 && cnpj.charAt(13) == check2;
        } catch (Exception e) {
            return false;
        }
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
