package br.ibict.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cnae entity.
 */
public class CnaeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer level;

    @NotNull
    private String cod;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CnaeDTO cnaeDTO = (CnaeDTO) o;
        if (cnaeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cnaeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CnaeDTO{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", cod='" + getCod() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
