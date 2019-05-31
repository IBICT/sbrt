package br.ibict.domain;

import java.time.Instant;

public class AnswerSummary {

    private Long id;

    private String title;

    private String description;

    private Instant datePublished;

    private Integer timesSeen;

    private Long questionID;

    private Long legalEntityID;

    public AnswerSummary(Long id, String title, String description, Instant datePublished, Integer timesSeen, Long questionID, Long legalEntityID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datePublished = datePublished;
        this.timesSeen = timesSeen;
        this.questionID = questionID;
        this.legalEntityID = legalEntityID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Instant datePublished) {
        this.datePublished = datePublished;
    }

    public Integer getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(Integer timesSeen) {
        this.timesSeen = timesSeen;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public Long getLegalEntityID() {
        return legalEntityID;
    }

    public void setLegalEntityID(Long legalEntityID) {
        this.legalEntityID = legalEntityID;
    }

}