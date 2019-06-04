package br.ibict.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;

/**
 * A DTO for the Answer entity.
 */
public class AnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private Instant datePublished;

    @Lob
    private String content;

    @Min(value = 0)
    private Integer timesSeen;

    private Boolean isReferentialOnly = Boolean.FALSE;

    private Long userId;

    private Long questionId;

    private Long legalEntityId;

    private Long cnaeId;

    // IDs of answers referenced
    private Set<Long> references = new HashSet<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(Integer timesSeen) {
        this.timesSeen = timesSeen;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Long legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Long getCnaeId() {
        return cnaeId;
    }

    public void setCnaeId(Long cnaeId) {
        this.cnaeId = cnaeId;
    }

    public Boolean getIsReferentialOnly() {
        return isReferentialOnly;
    }

    public void setIsReferentialOnly(Boolean isReferentialOnly) {
        this.isReferentialOnly = isReferentialOnly;
    }

    public Set<Long> getReferences() {
        return references;
    }

    public void setReferences(Set<Long> references) {
        this.references = references;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDTO answerDTO = (AnswerDTO) o;
        if (answerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", datePublished='" + getDatePublished() + "'" +
            ", content='" + getContent() + "'" +
            ", timesSeen=" + getTimesSeen() +
            ", user=" + getUserId() +
            ", question=" + getQuestionId() +
            ", legalEntity=" + getLegalEntityId() +
            ", cnae=" + getCnaeId() +
            "}";
    }
}
