package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.CnaeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cnae and its DTO CnaeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CnaeMapper extends EntityMapper<CnaeDTO, Cnae> {


    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "legalEntities", ignore = true)
    Cnae toEntity(CnaeDTO cnaeDTO);

    default Cnae fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cnae cnae = new Cnae();
        cnae.setId(id);
        return cnae;
    }
}
