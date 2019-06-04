package br.ibict.service.mapper;

import br.ibict.domain.*;
import br.ibict.service.dto.AnswerDTO;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, QuestionMapper.class, LegalEntityMapper.class, CnaeMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {
    Logger log = LoggerFactory.getLogger(AnswerMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "legalEntity.id", target = "legalEntityId")
    @Mapping(source = "cnae.id", target = "cnaeId")
    @Mapping(source = "references", target = "references")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "legalEntityId", target = "legalEntity")
    @Mapping(source = "cnaeId", target = "cnae")
    @Mapping(source = "references", target = "references")
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

    default Set<Answer> longSetToAnswerSet(Set<Long> ids) {
        log.debug(ids.toString());
        
        return ids.stream().map(i -> this.fromId(i)).collect(Collectors.toSet());
    }

    default Set<Long> answerSetToLongSet(Set<Answer> answers) {
        return answers.stream().map(i -> i.getId()).collect(Collectors.toSet());
    }
}
