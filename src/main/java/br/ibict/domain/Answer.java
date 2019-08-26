package br.ibict.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date_published", nullable = false)
    private Instant datePublished;

    @Column(name = "content")
    private String content;

    @Min(value = 0)
    @Column(name = "times_seen")
    private Integer timesSeen;

    @Column(name = "is_referential_only")
    private Boolean isReferentialOnly;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JsonIgnore
    private LegalEntity legalEntity;

    @ManyToOne
    @JsonIgnore
    private Cnae cnae;

    @OneToMany(mappedBy = "answer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "answer_answer_references",
        joinColumns = {@JoinColumn(name = "original_answer_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "reference_answer_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> references = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Answer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Answer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDatePublished() {
        return datePublished;
    }

    public Answer datePublished(Instant datePublished) {
        this.datePublished = datePublished;
        return this;
    }

    public void setDatePublished(Instant datePublished) {
        this.datePublished = datePublished;
    }

    public String getContent() {
        return content;
    }

    public Answer content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTimesSeen() {
        return timesSeen;
    }

    public Answer timesSeen(Integer timesSeen) {
        this.timesSeen = timesSeen;
        return this;
    }

    public void setTimesSeen(Integer timesSeen) {
        this.timesSeen = timesSeen;
    }

    public User getUser() {
        return user;
    }

    public Answer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public Answer legalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
        return this;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    public Cnae getCnae() {
        return cnae;
    }

    public Answer cnae(Cnae cnae) {
        this.cnae = cnae;
        return this;
    }

    public void setCnae(Cnae cnae) {
        this.cnae = cnae;
    }

    @JsonProperty("keywords")
    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public Answer keywords(Set<Keyword> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Answer addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.setAnswer(this);
        return this;
    }

    public Answer removeKeyword(Keyword keyword) {
        this.keywords.remove(keyword);
        keyword.setAnswer(null);
        return this;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Boolean getIsReferentialOnly() {
        return isReferentialOnly;
    }

    public void setIsReferentialOnly(Boolean isReferentialOnly) {
        this.isReferentialOnly = isReferentialOnly;
    }

    public Set<Answer> getReferences() {
        return references;
    }

    public void setReferences(Set<Answer> references) {
        this.references = references;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Integer incrementSeen() {
        return ++this.timesSeen;
    }

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("userId")
    public Long getUserId() {
        return this.user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        if (answer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", datePublished='" + getDatePublished() + "'" +
            ", content='" + getContent() + "'" +
            ", timesSeen=" + getTimesSeen() +
            ", keywords=" + getKeywords() +
            "}";
    }

}
