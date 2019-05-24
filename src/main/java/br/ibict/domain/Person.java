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

import br.ibict.domain.enumeration.Schooling;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "schooling")
    private Schooling schooling;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "zip_code")
    private String zipCode;

    @NotNull
    @Size(max = 2)
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_legal_entities",
               joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "legal_entities_id", referencedColumnName = "id"))
    private Set<LegalEntity> legalEntities = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public Person cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Schooling getSchooling() {
        return schooling;
    }

    public Person schooling(Schooling schooling) {
        this.schooling = schooling;
        return this;
    }

    public void setSchooling(Schooling schooling) {
        this.schooling = schooling;
    }

    public Integer getGender() {
        return gender;
    }

    public Person gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public Person city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public Person address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumber() {
        return number;
    }

    public Person number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public Person complement(String complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Person zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUf() {
        return uf;
    }

    public Person uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Set<LegalEntity> getLegalEntities() {
        return legalEntities;
    }

    public Person legalEntities(Set<LegalEntity> legalEntities) {
        this.legalEntities = legalEntities;
        return this;
    }

    public Person addLegalEntities(LegalEntity legalEntity) {
        this.legalEntities.add(legalEntity);
        legalEntity.getPersons().add(this);
        return this;
    }

    public Person removeLegalEntities(LegalEntity legalEntity) {
        this.legalEntities.remove(legalEntity);
        legalEntity.getPersons().remove(this);
        return this;
    }

    public void setLegalEntities(Set<LegalEntity> legalEntities) {
        this.legalEntities = legalEntities;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Person contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Person addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setPerson(this);
        return this;
    }

    public Person removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setPerson(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public boolean representsLegalEntity(Long legalEntityId) {
        return this.legalEntities.stream().anyMatch(l -> l.getId().equals(legalEntityId));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", schooling='" + getSchooling() + "'" +
            ", gender=" + getGender() +
            ", city='" + getCity() + "'" +
            ", address='" + getAddress() + "'" +
            ", number=" + getNumber() +
            ", complement='" + getComplement() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", uf='" + getUf() + "'" +
            ", legalEntities='" + getLegalEntities().toString() + "'" +
            "}";
    }
}
