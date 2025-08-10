package com.orcafacil.api.infrastructure.persistence.entity.visitnegotiation;

import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import com.orcafacil.api.domain.visitnegotiation.Propeser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "visit_negotiation")
public class VisitNegotiationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Enumerated(EnumType.STRING)
    @Column(name = "proposer", nullable = false)
    private Propeser proposer;

    @Temporal(TemporalType.DATE)
    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @Column(name = "sent_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sentDate;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    public VisitNegotiationEntity() {}

    public VisitNegotiationEntity(Integer id, ServiceEntity service, Propeser proposer, Date visitDate, LocalDateTime sentDate, Boolean accepted) {
        this.id = id;
        this.service = service;
        this.proposer = proposer;
        this.visitDate = visitDate;
        this.sentDate = sentDate;
        this.accepted = accepted;
    }

    // Getters e setters
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public ServiceEntity getService() {return service;}

    public void setService(ServiceEntity service) {this.service = service;}

    public Propeser getProposer() {return proposer;}

    public void setProposer(Propeser proposer) {this.proposer = proposer;}

    public Date getVisitDate() {return visitDate;}

    public void setVisitDate(Date visitDate) {this.visitDate = visitDate;}

    public LocalDateTime getSentDate() {return sentDate;}

    public void setSentDate(LocalDateTime sentDate) {this.sentDate = sentDate;}

    public Boolean getAccepted() {return accepted;}

    public void setAccepted(Boolean accepted) {this.accepted = accepted;}

    @Override
    public String toString() {
        return "VisitNegotiationEntity{" +
                "id=" + id +
                ", service=" + service +
                ", proposer=" + proposer +
                ", visitDate=" + visitDate +
                ", sentDate=" + sentDate +
                ", accepted=" + accepted +
                '}';
    }
}
