package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {LegalEntityMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {


    @Mapping(target = "contacts", ignore = true)
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
