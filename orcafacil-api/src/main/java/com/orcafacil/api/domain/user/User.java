package com.orcafacil.api.domain.user;

import com.orcafacil.api.domain.exception.DomainException;

import java.util.Date;
import java.util.Objects;

public class User {

    private final Integer id;
    private String name;
    private String phone;
    private String cpf;
    private UserType userType;
    private Date birthDate;
    private UserStatus status;
    private Integer addressId;

    public User(Integer id,
                String name,
                String phone,
                String cpf,
                UserType userType,
                Date birthDate,
                UserStatus status,
                Integer addressId) {

        this.id = id;
        setName(name);
        setPhone(phone);
        setCpf(cpf);
        setUserType(userType);
        setBirthDate(birthDate);
        setStatus(status);
        setAddressId(addressId);
    }

    public boolean isActive() {
        return this.status == UserStatus.APROVADO;
    }

    public void changeName(String newName) {
        setName(newName);
    }

    public void changePhone(String newPhone) {
        setPhone(newPhone);
    }

    public void changeCpf(String newCpf) {
        setCpf(newCpf);
    }

    public void changeUserType(UserType newUserType) {
        setUserType(newUserType);
    }

    public void changeBirthDate(Date newBirthDate) {
        setBirthDate(newBirthDate);
    }

    public void changeStatus(UserStatus newStatus) {
        setStatus(newStatus);
    }

    public void changeAddressId(Integer newAddressId) {
        setAddressId(newAddressId);
    }

    // Validações essenciais

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException("Nome é obrigatório e não pode estar vazio.");
        }
        if (name.length() > 100) {
            throw new DomainException("Nome não pode exceder 100 caracteres.");
        }
        this.name = name;
    }

    private void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new DomainException("Telefone é obrigatório.");
        }
        String digits = phone.replaceAll("\\D", "");
        if (digits.length() < 10) {
            throw new DomainException("Telefone deve conter ao menos 10 dígitos numéricos.");
        }
        this.phone = phone;
    }

    private void setCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new DomainException("CPF é obrigatório.");
        }
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new DomainException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        this.cpf = digits;
    }

    private void setUserType(UserType userType) {
        if (userType == null) {
            throw new DomainException("Tipo de usuário é obrigatório.");
        }
        this.userType = userType;
    }

    private void setBirthDate(Date birthDate) {
        if (birthDate == null) {
            throw new DomainException("Data de nascimento é obrigatória.");
        }
        if (birthDate.after(new Date())) {
            throw new DomainException("Data de nascimento não pode ser no futuro.");
        }
        this.birthDate = birthDate;
    }

    private void setStatus(UserStatus status) {
        if (status == null) {
            throw new DomainException("Status do usuário é obrigatório.");
        }
        this.status = status;
    }

    private void setAddressId(Integer addressId) {
        if (addressId == null) {
            throw new DomainException("Endereço é obrigatório.");
        }
        this.addressId = addressId;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCpf() {
        return cpf;
    }

    public UserType getUserType() {
        return userType;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Integer getAddressId() {
        return addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
