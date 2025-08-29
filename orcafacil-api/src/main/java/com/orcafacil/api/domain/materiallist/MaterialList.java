package com.orcafacil.api.domain.materiallist;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class MaterialList {

    private final Integer id;
    private final Service service;
    private final String nomeMaterial;
    private final Integer quantity;
    private final BigDecimal unitPrice;

    public MaterialList(Integer id, Service service, String nomeMaterial, Integer quantity, BigDecimal unitPrice) {
        this.id = id;
        this.service = service;
        this.nomeMaterial = nomeMaterial;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {return id;}

    public Service getService() {return service;}

    public String getNomeMaterial() {return nomeMaterial;}

    public Integer getQuantity() {return quantity;}

    public BigDecimal getUnitPrice() {return unitPrice;}

    public MaterialList withId(Integer newId) {return new MaterialList(newId, service, nomeMaterial, quantity, unitPrice);}

    public MaterialList withService(Service newService) {return new MaterialList(id, newService, nomeMaterial, quantity, unitPrice);}

    public MaterialList withNomeMaterial(String newNomeMaterial) {return new MaterialList(id, service, newNomeMaterial, quantity, unitPrice);}

    public MaterialList withQuantity(Integer newQuantity) {return new MaterialList(id, service, nomeMaterial, newQuantity, unitPrice);}

    public MaterialList withUnitPrice(BigDecimal newUnitPrice) {return new MaterialList(id, service, nomeMaterial, quantity, newUnitPrice);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialList)) return false;
        MaterialList that = (MaterialList) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(service, that.service) &&
                Objects.equals(nomeMaterial, that.nomeMaterial) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service, nomeMaterial, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "MaterialListEntity{" +
                "id=" + id +
                ", service=" + service +
                ", nomeMaterial='" + nomeMaterial + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
