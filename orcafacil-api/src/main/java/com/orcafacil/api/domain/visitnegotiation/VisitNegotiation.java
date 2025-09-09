package com.orcafacil.api.domain.visitnegotiation;

import com.orcafacil.api.domain.service.Service;

import java.time.LocalDateTime;
import java.util.Date;

public class VisitNegotiation {
    private final Integer id;
    private final Service service;
    private final Propeser propeser;
    private final Date visitDate;
    private final LocalDateTime sentDate;
    private final Boolean accept;

    public VisitNegotiation(Integer id, Service service, Propeser propeser, Date visitDate, LocalDateTime sentDate, Boolean accept) {
        this.id = id;
        this.service = service;
        this.propeser = propeser;
        this.visitDate = visitDate;
        this.sentDate = sentDate;
        this.accept = accept;
    }

    public Integer getId() {return id;}

    public Service getService() {return service;}

    public Propeser getPropeser() {return propeser;}

    public Date getVisitDate() {return visitDate;}

    public LocalDateTime getSentDate() {return sentDate;}

    public Boolean getAccept() {return accept;}

    // Métodos withX
    public VisitNegotiation withService(Service newService){
        return  new VisitNegotiation(id,newService,propeser,visitDate,sentDate,accept);
    }

    public VisitNegotiation withPropeser(Propeser newPropeser){
        return  new VisitNegotiation(id,service,newPropeser,visitDate,sentDate,accept);
    }

    public VisitNegotiation withVisitDate(Date newVisitDate ){
        return  new VisitNegotiation(id,service,propeser,newVisitDate,sentDate,accept);
    }
    public VisitNegotiation withSentDate(LocalDateTime newSentDate){
        return  new VisitNegotiation(id,service,propeser,visitDate,newSentDate,accept);
    }
    public VisitNegotiation withAccept(Boolean newAccept){
        return  new VisitNegotiation(id,service,propeser,visitDate,sentDate,newAccept);
    }

    @Override
    public String toString() {
        return "VisitNegotiation{" +
                "id=" + id +
                ", service=" + service +
                ", propeser=" + propeser +
                ", visitDate=" + visitDate +
                ", sentDate=" + sentDate +
                ", accept=" + accept +
                '}';
    }
}
