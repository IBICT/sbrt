package br.ibict.service.impl;

import br.ibict.service.CnaeService;
import br.ibict.domain.Cnae;
import br.ibict.repository.CnaeRepository;
import br.ibict.service.dto.CnaeDTO;
import br.ibict.service.mapper.CnaeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Cnae.
 */
@Service
@Transactional
public class CnaeServiceImpl implements CnaeService {

    private final Logger log = LoggerFactory.getLogger(CnaeServiceImpl.class);

    private final CnaeRepository cnaeRepository;

    private final CnaeMapper cnaeMapper;

    public CnaeServiceImpl(CnaeRepository cnaeRepository, CnaeMapper cnaeMapper) {
        this.cnaeRepository = cnaeRepository;
        this.cnaeMapper = cnaeMapper;
    }

    /**
     * Save a cnae.
     *
     * @param cnaeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CnaeDTO save(CnaeDTO cnaeDTO) {
        log.debug("Request to save Cnae : {}", cnaeDTO);
        Cnae cnae = cnaeMapper.toEntity(cnaeDTO);
        cnae = cnaeRepository.save(cnae);
        return cnaeMapper.toDto(cnae);
    }

    /**
     * Get all the cnaes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CnaeDTO> findAll() {
        log.debug("Request to get all Cnaes");
        return cnaeRepository.findAll().stream()
            .map(cnaeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cnae by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CnaeDTO> findOne(Long id) {
        log.debug("Request to get Cnae : {}", id);
        return cnaeRepository.findById(id)
            .map(cnaeMapper::toDto);
    }

    /**
     * Delete the cnae by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cnae : {}", id);
        cnaeRepository.deleteById(id);
    }
}
