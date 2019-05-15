package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.LegalEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LegalEntity and its DTO LegalEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {CnaeMapper.class})
public interface LegalEntityMapper extends EntityMapper<LegalEntityDTO, LegalEntity> {

    @Mapping(source = "cnae.id", target = "cnaeId")
    LegalEntityDTO toDto(LegalEntity legalEntity);

    @Mapping(source = "cnaeId", target = "cnae")
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "persons", ignore = true)
    LegalEntity toEntity(LegalEntityDTO legalEntityDTO);

    default LegalEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(id);
        return legalEntity;
    }
}
