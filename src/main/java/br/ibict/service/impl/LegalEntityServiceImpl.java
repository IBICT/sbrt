package br.ibict.service.impl;

import br.ibict.service.LegalEntityService;
import br.ibict.domain.LegalEntity;
import br.ibict.repository.LegalEntityRepository;
import br.ibict.service.dto.LegalEntityDTO;
import br.ibict.service.mapper.LegalEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LegalEntity.
 */
@Service
@Transactional
public class LegalEntityServiceImpl implements LegalEntityService {

    private final Logger log = LoggerFactory.getLogger(LegalEntityServiceImpl.class);

    private final LegalEntityRepository legalEntityRepository;

    private final LegalEntityMapper legalEntityMapper;

    public LegalEntityServiceImpl(LegalEntityRepository legalEntityRepository, LegalEntityMapper legalEntityMapper) {
        this.legalEntityRepository = legalEntityRepository;
        this.legalEntityMapper = legalEntityMapper;
    }

    /**
     * Save a legalEntity.
     *
     * @param legalEntityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LegalEntityDTO save(LegalEntityDTO legalEntityDTO) {
        log.debug("Request to save LegalEntity : {}", legalEntityDTO);
        LegalEntity legalEntity = legalEntityMapper.toEntity(legalEntityDTO);
        legalEntity = legalEntityRepository.save(legalEntity);
        return legalEntityMapper.toDto(legalEntity);
    }

    /**
     * Get all the legalEntities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LegalEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LegalEntities");
        return legalEntityRepository.findAll(pageable)
            .map(legalEntityMapper::toDto);
    }


    /**
     * Get one legalEntity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LegalEntityDTO> findOne(Long id) {
        log.debug("Request to get LegalEntity : {}", id);
        return legalEntityRepository.findById(id)
            .map(legalEntityMapper::toDto);
    }

    /**
     * Delete the legalEntity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LegalEntity : {}", id);
        legalEntityRepository.deleteById(id);
    }
}
