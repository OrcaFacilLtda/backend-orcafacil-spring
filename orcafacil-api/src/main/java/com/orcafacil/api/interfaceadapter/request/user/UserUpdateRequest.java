package com.orcafacil.api.interfaceadapter.request.user;

import com.orcafacil.api.domain.address.Address;
import jakarta.validation.constraints.*;
import java.util.Date;

public class UserUpdateRequest {

    @Size(max = 100, message = "Nome não pode exceder 100 caracteres.")
    private String name;

    @Pattern(regexp = "\\d{10,}", message = "Telefone deve conter ao menos 10 dígitos numéricos.")
    private String phone;

    @Email(message = "Email deve ser válido.")
    private String email;

    private String password;

    private String currentPassword;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos.")
    private String cpf;

    private String userType;

    private Date birthDate;

    private String status;

    private Address address;

    // Getters e setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
