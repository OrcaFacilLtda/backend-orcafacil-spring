package com.orcafacil.api.infrastructure.persistence.entity.materiallist;

import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "material_list")
public class MaterialListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = "nome_material", length = 100)
    private String nomeMaterial;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public MaterialListEntity() {
    }

    public MaterialListEntity(Integer id, ServiceEntity service, String nomeMaterial, Integer quantity, BigDecimal unitPrice) {
        this.id = id;
        this.service = service;
        this.nomeMaterial = nomeMaterial;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public String getNomeMaterial() {
        return nomeMaterial;
    }

    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialListEntity)) return false;
        MaterialListEntity that = (MaterialListEntity) o;
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
