package com.orcafacil.api.domain.evaluation;

import com.orcafacil.api.domain.service.Service;

import java.time.LocalDateTime;

public class Evaluation {
    private final Integer id;
    private final Service service;
    private final Integer stars;
    private final LocalDateTime evaluationDate;

    public Evaluation(Integer id, Service service, Integer stars, LocalDateTime evaluationDate) {
        this.id = id;
        this.service = service;
        this.stars = stars;
        this.evaluationDate = evaluationDate;
    }

    public Integer getId() {return id;}

    public Service getService() {return service;}

    public Integer getStars() {return stars;}

    public LocalDateTime getEvaluationDate() {return evaluationDate;}

    // Métodos withX
    public Evaluation withService(Service newService){
        return new Evaluation(id,newService,stars,evaluationDate);
    }

    public Evaluation withStars(Integer newStars){
        return new Evaluation(id,service,newStars,evaluationDate);
    }

    public Evaluation withEvaluationDate(LocalDateTime newEvaluationDate){
        return new Evaluation(id,service,stars,newEvaluationDate);
    }


    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", service=" + service +
                ", stars=" + stars +
                ", evaluationDate=" + evaluationDate +
                '}';
    }
}
