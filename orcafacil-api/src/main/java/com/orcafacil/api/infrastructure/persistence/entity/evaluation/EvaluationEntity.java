package com.orcafacil.api.infrastructure.persistence.entity.evaluation;

import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Min(0)
    @Max(5)
    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "evaluation_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime evaluationDate;

    public EvaluationEntity() {}

    public EvaluationEntity(Integer id, ServiceEntity service, Integer stars, LocalDateTime evaluationDate) {
        this.id = id;
        this.service = service;
        this.stars = stars;
        this.evaluationDate = evaluationDate;
    }

    @PrePersist
    protected void onCreate() {
        if (evaluationDate == null) {
            evaluationDate = LocalDateTime.now();
        }
    }

    // Getters e setters
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public ServiceEntity getService() {return service;}

    public void setService(ServiceEntity service) {this.service = service;}

    public Integer getStars() {return stars;}

    public void setStars(Integer stars) {this.stars = stars;}

    public LocalDateTime getEvaluationDate() {return evaluationDate;}

    public void setEvaluationDate(LocalDateTime evaluationDate) {this.evaluationDate = evaluationDate;}

    @Override
    public String toString() {
        return "EvaluationEntity{" +
                "id=" + id +
                ", service=" + service +
                ", stars=" + stars +
                ", evaluationDate=" + evaluationDate +
                '}';
    }
}
