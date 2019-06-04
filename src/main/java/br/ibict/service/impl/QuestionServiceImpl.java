package br.ibict.service.impl;

import br.ibict.service.QuestionService;
import br.ibict.domain.Question;
import br.ibict.repository.QuestionRepository;
import br.ibict.service.dto.QuestionDTO;
import br.ibict.service.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing Question.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionRepository.findAll(pageable)
            .map(questionMapper::toDto);
    }


    /**
     * Get one question by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id)
            .map(questionMapper::toDto);
    }

    /**
     * Delete the question by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }

    public QuestionDTO changeHandler(Long questionId, Long handlerId) {

        Question question = null;
        Optional<Question> questionOPT = questionRepository.findById(questionId);
        if(questionOPT.isPresent()) {
            question = questionOPT.get();
        } else {
            throw new EntityNotFoundException("Question not found");
        }
        QuestionDTO questionDTO = questionMapper.toDto(question);
        questionDTO.setLegalEntityId(handlerId);
        questionRepository.save(questionMapper.toEntity(questionDTO));

        return questionDTO;

    }


    @Override
    public Page<QuestionDTO> findAllByLegalEntity(Pageable pageable, Long legalEntityId) {
        log.debug("Request to get all Questions for a legal entity");
        return questionRepository.findByLegalEntityId(pageable, legalEntityId)
            .map(questionMapper::toDto);
    }

    @Override
	public Page<QuestionDTO> findByUser(Long id, Pageable pageable) {
        return this.questionRepository.findByUserId(id, pageable).map(questionMapper::toDto);

    }

}
