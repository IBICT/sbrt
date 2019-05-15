package br.ibict.service;

import br.ibict.service.dto.CnaeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Cnae.
 */
public interface CnaeService {

    /**
     * Save a cnae.
     *
     * @param cnaeDTO the entity to save
     * @return the persisted entity
     */
    CnaeDTO save(CnaeDTO cnaeDTO);

    /**
     * Get all the cnaes.
     *
     * @return the list of entities
     */
    List<CnaeDTO> findAll();


    /**
     * Get the "id" cnae.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CnaeDTO> findOne(Long id);

    /**
     * Delete the "id" cnae.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
