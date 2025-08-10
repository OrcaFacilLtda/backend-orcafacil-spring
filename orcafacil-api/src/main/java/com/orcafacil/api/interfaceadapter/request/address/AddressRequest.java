package com.orcafacil.api.interfaceadapter.request.address;

import jakarta.validation.constraints.*;

public class AddressRequest {

    @NotNull(message = "ID é obrigatório")
    @Min(value = 1, message = "ID deve ser maior que zero")
    private Integer id;

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 100)
    private String street;

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 10)
    private String number;

    @Size(max = 50)
    private String complement;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 50)
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String state;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String zipCode;


    // Getters e Setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
}