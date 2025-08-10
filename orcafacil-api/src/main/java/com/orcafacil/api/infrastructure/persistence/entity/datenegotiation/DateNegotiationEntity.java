package com.orcafacil.api.infrastructure.persistence.entity.datenegotiation;

import com.orcafacil.api.domain.datenegotiation.Propeser;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "date_negotiation")
public class DateNegotiationEntity {

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
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "sent_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sentDate;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    public DateNegotiationEntity() {
    }

    public DateNegotiationEntity(Integer id, ServiceEntity service, Propeser proposer, Date startDate, Date endDate, LocalDateTime sentDate, Boolean accepted) {
        this.id = id;
        this.service = service;
        this.proposer = proposer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sentDate = sentDate;
        this.accepted = accepted;
    }

    @PrePersist
    protected void onCreate() {
        if (sentDate == null) {
            sentDate = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public ServiceEntity getService() {return service;}

    public void setService(ServiceEntity service) {this.service = service;}

    public Propeser getProposer() {return proposer;}

    public void setProposer(Propeser proposer) {this.proposer = proposer;}

    public Date getStartDate() {return startDate;}

    public void setStartDate(Date startDate) {this.startDate = startDate;}

    public Date getEndDate() {return endDate;}

    public void setEndDate(Date endDate) {this.endDate = endDate;}

    public LocalDateTime getSentDate() {return sentDate;}

    public void setSentDate(LocalDateTime sentDate) {this.sentDate = sentDate;}

    public Boolean getAccepted() {return accepted;}

    public void setAccepted(Boolean accepted) {this.accepted = accepted;}

    @Override
    public String toString() {
        return "DateNegotiationEntity{" +
                "id=" + id +
                ", service=" + service +
                ", proposer=" + proposer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sentDate=" + sentDate +
                ", accepted=" + accepted +
                '}';
    }
}
