package com.orcafacil.api.interfaceadapter.request.user;

import com.orcafacil.api.domain.address.Address;
import jakarta.validation.constraints.*;
import java.util.Date;

public class UserRequest {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres.")
    private String name;

    @NotBlank(message = "Telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,}", message = "Telefone deve conter ao menos 10 dígitos numéricos.")
    private String phone;


    @NotBlank(message = "Email é obrigatório.")
    private String email;


    @NotBlank(message = "Senha é obrigatório.")
    private String password;


    @NotBlank(message = "CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos.")
    private String cpf;

    @NotNull(message = "Tipo de usuário é obrigatório.")
    private String userType;

    @NotNull(message = "Data de nascimento é obrigatória.")
    private Date birthDate;

    @NotNull(message = "Status é obrigatório.")
    private String status;

    @NotNull(message = "Id do Endereço é obrigatório.")
    private Address address;

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

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

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

    public void setAddressId(Address address) {
        this.address = address;
    }
}
