package br.ibict.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LegalEntity entity.
 */
public class LegalEntityDTO implements Serializable {

    private Long id;

    @Size(min = 14, max = 14)
    private String cnpj;

    @NotNull
    private String name;

    private String address;

    private Integer number;

    private String complement;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    @NotNull
    @Size(max = 2)
    private String uf;


    private Long cnaeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getCnaeId() {
        return cnaeId;
    }

    public void setCnaeId(Long cnaeId) {
        this.cnaeId = cnaeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LegalEntityDTO legalEntityDTO = (LegalEntityDTO) o;
        if (legalEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), legalEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LegalEntityDTO{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", number=" + getNumber() +
            ", complement='" + getComplement() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", uf='" + getUf() + "'" +
            ", cnae=" + getCnaeId() +
            "}";
    }
}
