package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, QuestionMapper.class, LegalEntityMapper.class, CnaeMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "legalEntity.id", target = "legalEntityId")
    @Mapping(source = "cnae.id", target = "cnaeId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "legalEntityId", target = "legalEntity")
    @Mapping(source = "cnaeId", target = "cnae")
    @Mapping(target = "keywords", ignore = true)
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
