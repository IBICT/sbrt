package br.ibict.web.rest.vm;

import br.ibict.domain.Contact;
import br.ibict.domain.LegalEntity;
import br.ibict.domain.enumeration.Schooling;
import br.ibict.service.dto.ContactDTO;
import br.ibict.service.dto.LegalEntityDTO;
import br.ibict.service.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private String name;

    private String uf;

    private String cpf;

    private Schooling schooling;

    private Integer gender;

    private String city;

    private String address;

    private Integer number;

    private String complement;

    private String zipCode;

    private Set<LegalEntityDTO> legalEntities = new HashSet<>();

    private Set<ContactDTO> contacts = new HashSet<>();

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public Set<LegalEntityDTO> getLegalEntities() {
        return legalEntities;
    }

    public void setLegalEntities(Set<LegalEntityDTO> legalEntities) {
        this.legalEntities = legalEntities;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            super.toString() + " }";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Schooling getSchooling() {
        return schooling;
    }

    public void setSchooling(Schooling schooling) {
        this.schooling = schooling;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
