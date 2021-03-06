package br.ibict.service;

import br.ibict.domain.Answer;
import br.ibict.service.dto.AnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Answer.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save
     * @return the persisted entity
     */
    AnswerDTO save(AnswerDTO answerDTO);

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AnswerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" answer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AnswerDTO> findOne(Long id);

    /**
     * Delete the "id" answer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the answer regarding question "id".
     *
     * @param id the id of the question
     * @return the entity
     */
    Optional<AnswerDTO> findByQuestion(Long id);


    /**
     * Increments the number of times this answer was seen. If not present, does nothing
     * @param answerDTO
     * @return the same answerDTO with the timesSeen field increased by one
     */
    Optional<AnswerDTO> incrementAnswerSeen(Optional<AnswerDTO> answerDTO);

    Page<Answer> getSummaries(Pageable pageable);

    void rateAnswer(Short rating, Long userID, Long answerID);

}
