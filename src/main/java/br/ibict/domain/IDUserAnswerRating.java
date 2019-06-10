package br.ibict.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IDUserAnswerRating implements Serializable {

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="answer_id", nullable=false)
    private Long answerId;

    public IDUserAnswerRating() { }

    public IDUserAnswerRating(Long userId, Long answerId) {
        this.userId = userId;
        this.answerId = answerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((answerId == null) ? 0 : answerId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IDUserAnswerRating other = (IDUserAnswerRating) obj;
        if (answerId == null) {
            if (other.answerId != null)
                return false;
        } else if (!answerId.equals(other.answerId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IDUserAnswerRating [answerId=" + answerId + ", userId=" + userId + "]";
    }

}