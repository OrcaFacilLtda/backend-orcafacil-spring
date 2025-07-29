package com.orcafacil.api.domain.address;

import com.orcafacil.api.domain.exception.DomainException;

import java.util.Objects;

public class Address {
    private final Integer id;
    private final String zipCode;
    private final String street;
    private final String number;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String complement;

    public Address(Integer id, String zipCode, String street, String number,
                   String neighborhood, String city, String state, String complement) {
        validateZipCode(zipCode);
        validateStreet(street);
        validateNumber(number);
        validateNeighborhood(neighborhood);
        validateCity(city);
        validateState(state);
        validateComplement(complement);

        this.id = id;
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.complement = complement;
    }

    private void validateZipCode(String zipCode) {
        if (isNullOrEmpty(zipCode) || !zipCode.matches("\\d{8}")) {
            throw new DomainException("CEP inválido. Deve conter exatamente 8 dígitos numéricos.");
        }
    }

    private void validateStreet(String street) {
        if (isNullOrEmpty(street)) {
            throw new DomainException("Rua não pode ser vazia.");
        }
    }

    private void validateNumber(String number) {
        if (isNullOrEmpty(number)) {
            throw new DomainException("Número do endereço é obrigatório.");
        }
    }

    private void validateNeighborhood(String neighborhood) {
        if (isNullOrEmpty(neighborhood)) {
            throw new DomainException("Bairro é obrigatório.");
        }
    }

    private void validateCity(String city) {
        if (isNullOrEmpty(city)) {
            throw new DomainException("Cidade é obrigatória.");
        }
    }

    private void validateState(String state) {
        if (isNullOrEmpty(state) || state.length() != 2) {
            throw new DomainException("Estado deve conter a sigla de 2 letras (ex: SP, RJ).");
        }
    }

    private void validateComplement(String complement) {
        if (complement != null && complement.trim().length() > 50) {
            throw new DomainException("Complemento não pode ter mais que 50 caracteres.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public Integer getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getComplement() {
        return complement;
    }

    @Override
    public String toString() {
        String baseAddress = street + ", " + number;
        if (complement != null && !complement.trim().isEmpty()) {
            baseAddress += " " + complement;
        }
        return baseAddress + " - " + neighborhood + ", " + city + " - " + state + ", " + zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(neighborhood, address.neighborhood) &&
                Objects.equals(city, address.city) &&
                Objects.equals(complement, address.complement) &&
                Objects.equals(state, address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, street, number, neighborhood, city, state, complement);
    }
}