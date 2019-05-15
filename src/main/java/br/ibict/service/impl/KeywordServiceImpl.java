package br.ibict.service.impl;

import br.ibict.service.KeywordService;
import br.ibict.domain.Keyword;
import br.ibict.repository.KeywordRepository;
import br.ibict.service.dto.KeywordDTO;
import br.ibict.service.mapper.KeywordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Keyword.
 */
@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {

    private final Logger log = LoggerFactory.getLogger(KeywordServiceImpl.class);

    private final KeywordRepository keywordRepository;

    private final KeywordMapper keywordMapper;

    public KeywordServiceImpl(KeywordRepository keywordRepository, KeywordMapper keywordMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    /**
     * Save a keyword.
     *
     * @param keywordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KeywordDTO save(KeywordDTO keywordDTO) {
        log.debug("Request to save Keyword : {}", keywordDTO);
        Keyword keyword = keywordMapper.toEntity(keywordDTO);
        keyword = keywordRepository.save(keyword);
        return keywordMapper.toDto(keyword);
    }

    /**
     * Get all the keywords.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KeywordDTO> findAll() {
        log.debug("Request to get all Keywords");
        return keywordRepository.findAll().stream()
            .map(keywordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one keyword by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KeywordDTO> findOne(Long id) {
        log.debug("Request to get Keyword : {}", id);
        return keywordRepository.findById(id)
            .map(keywordMapper::toDto);
    }

    /**
     * Delete the keyword by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Keyword : {}", id);
        keywordRepository.deleteById(id);
    }
}
