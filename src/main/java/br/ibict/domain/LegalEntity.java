package br.ibict.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LegalEntity.
 */
@Entity
@Table(name = "legal_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LegalEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 14, max = 14)
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "complement")
    private String complement;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Size(max = 2)
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @ManyToOne
    @JsonIgnoreProperties("legalEntities")
    private Cnae cnae;

    @OneToMany(mappedBy = "legalEntity")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

    @OneToMany(mappedBy = "legalEntity")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    @ManyToMany(mappedBy = "legalEntities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Person> persons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public LegalEntity cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public LegalEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public LegalEntity address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumber() {
        return number;
    }

    public LegalEntity number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public LegalEntity complement(String complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public LegalEntity city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public LegalEntity zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUf() {
        return uf;
    }

    public LegalEntity uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cnae getCnae() {
        return cnae;
    }

    public LegalEntity cnae(Cnae cnae) {
        this.cnae = cnae;
        return this;
    }

    public void setCnae(Cnae cnae) {
        this.cnae = cnae;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public LegalEntity answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public LegalEntity addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setLegalEntity(this);
        return this;
    }

    public LegalEntity removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setLegalEntity(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public LegalEntity questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public LegalEntity addQuestion(Question question) {
        this.questions.add(question);
        question.setLegalEntity(this);
        return this;
    }

    public LegalEntity removeQuestion(Question question) {
        this.questions.remove(question);
        question.setLegalEntity(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public LegalEntity persons(Set<Person> people) {
        this.persons = people;
        return this;
    }

    public LegalEntity addPersons(Person person) {
        this.persons.add(person);
        person.getLegalEntities().add(this);
        return this;
    }

    public LegalEntity removePersons(Person person) {
        this.persons.remove(person);
        person.getLegalEntities().remove(this);
        return this;
    }

    public void setPersons(Set<Person> people) {
        this.persons = people;
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
        LegalEntity legalEntity = (LegalEntity) o;
        if (legalEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), legalEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LegalEntity{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", number=" + getNumber() +
            ", complement='" + getComplement() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
