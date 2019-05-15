package br.ibict.service;

import br.ibict.service.dto.LegalEntityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LegalEntity.
 */
public interface LegalEntityService {

    /**
     * Save a legalEntity.
     *
     * @param legalEntityDTO the entity to save
     * @return the persisted entity
     */
    LegalEntityDTO save(LegalEntityDTO legalEntityDTO);

    /**
     * Get all the legalEntities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LegalEntityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" legalEntity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LegalEntityDTO> findOne(Long id);

    /**
     * Delete the "id" legalEntity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
