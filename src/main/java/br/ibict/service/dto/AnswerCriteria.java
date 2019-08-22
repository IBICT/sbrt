package br.ibict.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Answer entity. This class is used in AnswerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /answers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private InstantFilter datePublished;

    private IntegerFilter timesSeen;

    private LongFilter userId;

    private LongFilter questionId;

    private LongFilter legalEntityId;

    private LongFilter cnaeId;

    private LongFilter keywordId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(InstantFilter datePublished) {
        this.datePublished = datePublished;
    }

    public IntegerFilter getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(IntegerFilter timesSeen) {
        this.timesSeen = timesSeen;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    public LongFilter getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(LongFilter legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public LongFilter getCnaeId() {
        return cnaeId;
    }

    public void setCnaeId(LongFilter cnaeId) {
        this.cnaeId = cnaeId;
    }

    public LongFilter getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(LongFilter keywordId) {
        this.keywordId = keywordId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerCriteria that = (AnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(datePublished, that.datePublished) &&
            Objects.equals(timesSeen, that.timesSeen) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(questionId, that.questionId) &&
            Objects.equals(legalEntityId, that.legalEntityId) &&
            Objects.equals(cnaeId, that.cnaeId) &&
            Objects.equals(keywordId, that.keywordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        description,
        datePublished,
        timesSeen,
        userId,
        questionId,
        legalEntityId,
        cnaeId,
        keywordId
        );
    }

    @Override
    public String toString() {
        return "AnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (datePublished != null ? "datePublished=" + datePublished + ", " : "") +
                (timesSeen != null ? "timesSeen=" + timesSeen + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
                (legalEntityId != null ? "legalEntityId=" + legalEntityId + ", " : "") +
                (cnaeId != null ? "cnaeId=" + cnaeId + ", " : "") +
                (keywordId != null ? "keywordId=" + keywordId + ", " : "") +
            "}";
    }

}
