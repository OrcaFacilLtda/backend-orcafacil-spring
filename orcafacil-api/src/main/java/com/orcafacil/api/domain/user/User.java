package com.orcafacil.api.domain.user;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.exception.DomainException;

import java.util.Date;
import java.util.Objects;

public class User {

    private final Integer id;
    private final String name;
    private final String phone;
    private final String email;
    private final String password;
    private final String cpf;
    private final UserType userType;
    private final Date birthDate;
    private final UserStatus status;
    private final Address address;

    public User(Integer id,
                String name,
                String phone,
                String email,
                String password,
                String cpf,
                UserType userType,
                Date birthDate,
                UserStatus status,
                Address address) {

        validateName(name);
        validatePhone(phone);
        validateEmail(email);
        validatePassword(password);
        validateCpf(cpf);
        validateUserType(userType);
        validateBirthDate(birthDate);
        validateStatus(status);
        validateAddress(address);

        this.id = id;
        this.name = name.trim();
        this.phone = phone;
        this.email = email.trim().toLowerCase();
        this.password = password;
        this.cpf = cpf.replaceAll("\\D", "");
        this.userType = userType;
        this.birthDate = new Date(birthDate.getTime());
        this.status = status;
        this.address = address;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException("Nome é obrigatório e não pode estar vazio.");
        }
        if (name.length() > 100) {
            throw new DomainException("Nome não pode exceder 100 caracteres.");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new DomainException("Telefone é obrigatório.");
        }
        String digits = phone.replaceAll("\\D", "");
        if (digits.length() < 10) {
            throw new DomainException("Telefone deve conter ao menos 10 dígitos numéricos.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainException("E-mail é obrigatório.");
        }
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            throw new DomainException("E-mail inválido.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new DomainException("Senha é obrigatória.");
        }
        if (password.length() < 6) {
            throw new DomainException("Senha deve conter ao menos 6 caracteres.");
        }
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DomainException("CPF é obrigatório.");
        }
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new DomainException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
    }

    private void validateUserType(UserType userType) {
        if (userType == null) {
            throw new DomainException("Tipo de usuário é obrigatório.");
        }
    }

    private void validateBirthDate(Date birthDate) {
        if (birthDate == null) {
            throw new DomainException("Data de nascimento é obrigatória.");
        }
        if (birthDate.after(new Date())) {
            throw new DomainException("Data de nascimento não pode ser no futuro.");
        }
    }

    private void validateStatus(UserStatus status) {
        if (status == null) {
            throw new DomainException("Status do usuário é obrigatório.");
        }
    }

    private void validateAddress(Address address) {
        if (address == null) {
            throw new DomainException("Endereço é obrigatório.");
        }
    }

    public boolean isActive() {
        return this.status == UserStatus.APROVADO;
    }

    public User withName(String newName) {
        return new User(id, newName, phone, email, password, cpf, userType, birthDate, status, address);
    }

    public User withPhone(String newPhone) {
        return new User(id, name, newPhone, email, password, cpf, userType, birthDate, status, address);
    }

    public User withEmail(String newEmail) {
        return new User(id, name, phone, newEmail, password, cpf, userType, birthDate, status, address);
    }

    public User withPassword(String newPassword) {
        return new User(id, name, phone, email, newPassword, cpf, userType, birthDate, status, address);
    }

    public User withCpf(String newCpf) {
        return new User(id, name, phone, email, password, newCpf, userType, birthDate, status, address);
    }

    public User withUserType(UserType newType) {
        return new User(id, name, phone, email, password, cpf, newType, birthDate, status, address);
    }

    public User withBirthDate(Date newBirthDate) {
        return new User(id, name, phone, email, password, cpf, userType, newBirthDate, status, address);
    }

    public User withStatus(UserStatus newStatus) {
        return new User(id, name, phone, email, password, cpf, userType, birthDate, newStatus, address);
    }

    public User withAddress(Address newAddressId) {
        return new User(id, name, phone, email, password, cpf, userType, birthDate, status, newAddressId);
    }


    public Integer getId() {return id;}

    public String getName() {return name;}

    public String getPhone() {return phone;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public String getCpf() {return cpf;}

    public UserType getUserType() {return userType;}

    public Date getBirthDate() {return new Date(birthDate.getTime());}

    public UserStatus getStatus() {return status;}

    public Address getAddress() {return address;}


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
