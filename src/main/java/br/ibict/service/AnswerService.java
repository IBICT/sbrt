package br.ibict.service;

import br.ibict.domain.AnswerSummary;
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
     * Get all the answers from a given legal entity.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AnswerDTO> findAllByLegalEntity(Pageable pageable, Long legalEntityId);

    /**
     * Get all the answers by cnae
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AnswerDTO> findAllByCnae(Pageable pageable, String cnae);

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
     * Increments the number of times this answer was seen. If not present, does nothing
     * @param answerDTO
     * @return the same answerDTO with the timesSeen field increased by one
     */
    Optional<AnswerDTO> incrementAnswerSeen(Optional<AnswerDTO> answerDTO);

    Page<AnswerSummary> getSummaries(Pageable pageable);
}
