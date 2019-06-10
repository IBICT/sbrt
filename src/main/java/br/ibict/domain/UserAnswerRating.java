package br.ibict.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_answer_rating")
public class UserAnswerRating {

    @EmbeddedId
    private IDUserAnswerRating id;

    @Column(name="rating", nullable=false)
    private Short rating;

    public UserAnswerRating() { }

    public UserAnswerRating(Long userID, Long answerID, Short rating) {
        this.id = new IDUserAnswerRating(userID, answerID);
        this.rating = rating;
	}

	public IDUserAnswerRating getId() {
        return id;
    }

    public void setId(IDUserAnswerRating id) {
        this.id = id;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "UserAnswerRating [id=" + id + ", rating=" + rating + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        UserAnswerRating other = (UserAnswerRating) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}