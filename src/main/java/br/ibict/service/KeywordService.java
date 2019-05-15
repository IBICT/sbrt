package br.ibict.service;

import br.ibict.service.dto.KeywordDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Keyword.
 */
public interface KeywordService {

    /**
     * Save a keyword.
     *
     * @param keywordDTO the entity to save
     * @return the persisted entity
     */
    KeywordDTO save(KeywordDTO keywordDTO);

    /**
     * Get all the keywords.
     *
     * @return the list of entities
     */
    List<KeywordDTO> findAll();


    /**
     * Get the "id" keyword.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<KeywordDTO> findOne(Long id);

    /**
     * Delete the "id" keyword.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
