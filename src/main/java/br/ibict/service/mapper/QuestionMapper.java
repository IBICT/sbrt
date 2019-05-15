package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LegalEntityMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "legalEntity.id", target = "legalEntityId")
    QuestionDTO toDto(Question question);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "legalEntityId", target = "legalEntity")
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
