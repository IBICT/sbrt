package br.ibict.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Class for displaying answers in a simplified way (no content).
 * This requires a view in the DB. The :rating field is calculated as an avarege of ratings
 */
@Entity
@Immutable
@Table(name = "v_answer_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerSummary {

    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date_published", nullable = false)
    private Instant datePublished;

    @Column(name = "times_seen", nullable = false)
    private Integer timesSeen;

    @Column(name = "question_id", nullable = false)
    private Long questionID;

    @Column(name = "legal_entity_id", nullable = false)
    private Long legalEntityId;

    @Column(name = "cnae_cod")
    private String cnaeCod;

    @Column(name = "rating")
    private Double rating;

    public AnswerSummary() { }

    public AnswerSummary(Long id, String title, String description, Instant datePublished, Integer timesSeen, Long questionID, Long legalEntityID, String cnaeCod) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datePublished = datePublished;
        this.timesSeen = timesSeen;
        this.questionID = questionID;
        this.legalEntityId = legalEntityID;
        this.cnaeCod = cnaeCod;
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

    public Long getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Long legalEntityID) {
        this.legalEntityId = legalEntityID;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCnaeCod() {
        return cnaeCod;
    }

    public void setCnaeCod(String cnaeCod) {
        this.cnaeCod = cnaeCod;
    }

}