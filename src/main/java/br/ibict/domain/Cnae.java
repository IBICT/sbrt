package br.ibict.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cnae.
 */
@Entity
@Table(name = "cnae")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cnae implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Integer level;

    @NotNull
    @Column(name = "cod", nullable = false)
    private String cod;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "cnae")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();
    @OneToMany(mappedBy = "cnae")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LegalEntity> legalEntities = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public Cnae level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCod() {
        return cod;
    }

    public Cnae cod(String cod) {
        this.cod = cod;
        return this;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public Cnae description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Cnae answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Cnae addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setCnae(this);
        return this;
    }

    public Cnae removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setCnae(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<LegalEntity> getLegalEntities() {
        return legalEntities;
    }

    public Cnae legalEntities(Set<LegalEntity> legalEntities) {
        this.legalEntities = legalEntities;
        return this;
    }

    public Cnae addLegalEntity(LegalEntity legalEntity) {
        this.legalEntities.add(legalEntity);
        legalEntity.setCnae(this);
        return this;
    }

    public Cnae removeLegalEntity(LegalEntity legalEntity) {
        this.legalEntities.remove(legalEntity);
        legalEntity.setCnae(null);
        return this;
    }

    public void setLegalEntities(Set<LegalEntity> legalEntities) {
        this.legalEntities = legalEntities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cnae cnae = (Cnae) o;
        if (cnae.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cnae.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cnae{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", cod='" + getCod() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
