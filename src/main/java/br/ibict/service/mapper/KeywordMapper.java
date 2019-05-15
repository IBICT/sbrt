package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.KeywordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Keyword and its DTO KeywordDTO.
 */
@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface KeywordMapper extends EntityMapper<KeywordDTO, Keyword> {

    @Mapping(source = "answer.id", target = "answerId")
    KeywordDTO toDto(Keyword keyword);

    @Mapping(source = "answerId", target = "answer")
    Keyword toEntity(KeywordDTO keywordDTO);

    default Keyword fromId(Long id) {
        if (id == null) {
            return null;
        }
        Keyword keyword = new Keyword();
        keyword.setId(id);
        return keyword;
    }
}
