package com.orcafacil.api.infrastructure.persistence.entity.provider;

import com.orcafacil.api.infrastructure.persistence.entity.category.CategoryEntity;
import com.orcafacil.api.infrastructure.persistence.entity.company.CompanyEntity;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;
@Entity
@Table(name = "provider")
public class ProviderEntity {
    @Id
    private Integer id;

    @OneToOne(optional = false, cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserEntity user;


    @OneToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false, unique = true)
    private CompanyEntity company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

    public ProviderEntity() {}

    public ProviderEntity(UserEntity user, CompanyEntity company, CategoryEntity category) {
        this.user = user;
        this.company = company;
        this.category = category;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProviderEntity{" +
                "user=" + user +
                ", company=" + company +
                ", category=" + category +
                '}';
    }
}
