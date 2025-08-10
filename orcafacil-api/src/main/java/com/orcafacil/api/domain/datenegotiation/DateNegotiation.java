package com.orcafacil.api.domain.datenegotiation;

import com.orcafacil.api.domain.service.Service;

import java.time.LocalDateTime;
import java.util.Date;

public class DateNegotiation {
    private final Integer id;
    private final Service service;
    private final Propeser propeser;
    private final Date startDate;
    private final Date endDate;
    private  final LocalDateTime sentDate;
    private final Boolean accepted;

    public DateNegotiation(Integer id, Service service, Propeser propeser, Date startDate, Date endDate, LocalDateTime sentDate, Boolean accepted) {
        this.id = id;
        this.service = service;
        this.propeser = propeser;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sentDate = sentDate;
        this.accepted = accepted;
    }

    public Integer getId() {return id;}

    public Service getService() {return service;}

    public Propeser getPropeser() {return propeser;}

    public Date getStartDate() {return startDate;}

    public Date getEndDate() {return endDate;}

    public LocalDateTime getSentDate() {return sentDate;}

    public Boolean getAccepted() {return accepted;}


    public DateNegotiation withService(Service newService){
        return new DateNegotiation(id,newService,propeser,startDate,endDate,sentDate,accepted);
    }

    public DateNegotiation withPropeser(Propeser newProposer){
        return new DateNegotiation(id,service,newProposer,startDate,endDate,sentDate,accepted);
    }

    public DateNegotiation withStartDate(Date newStartDate){
        return new DateNegotiation(id,service,propeser,newStartDate,endDate,sentDate,accepted);
    }

    public DateNegotiation withEndDate(Date newEndDate){
        return new DateNegotiation(id,service,propeser,startDate,newEndDate,sentDate,accepted);
    }

    public DateNegotiation withSentDate(LocalDateTime newSentDate){
        return new DateNegotiation(id,service,propeser,startDate,endDate,newSentDate,accepted);
    }

    public DateNegotiation withAccepted(Boolean newAccepted){
        return new DateNegotiation(id,service,propeser,startDate,endDate,sentDate,newAccepted);
    }


    @Override
    public String toString() {
        return "DateNegotiation{" +
                "id=" + id +
                ", service=" + service +
                ", propeser=" + propeser +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sentDate=" + sentDate +
                ", accepted=" + accepted +
                '}';
    }
}
