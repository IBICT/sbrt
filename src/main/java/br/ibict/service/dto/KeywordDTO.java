package br.ibict.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Keyword entity.
 */
public class KeywordDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String word;


    private Long answerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KeywordDTO keywordDTO = (KeywordDTO) o;
        if (keywordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keywordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeywordDTO{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", answer=" + getAnswerId() +
            "}";
    }
}
