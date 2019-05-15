package br.ibict.web.rest;
import br.ibict.service.KeywordService;
import br.ibict.web.rest.errors.BadRequestAlertException;
import br.ibict.web.rest.util.HeaderUtil;
import br.ibict.service.dto.KeywordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Keyword.
 */
@RestController
@RequestMapping("/api")
public class KeywordResource {

    private final Logger log = LoggerFactory.getLogger(KeywordResource.class);

    private static final String ENTITY_NAME = "keyword";

    private final KeywordService keywordService;

    public KeywordResource(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     * POST  /keywords : Create a new keyword.
     *
     * @param keywordDTO the keywordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keywordDTO, or with status 400 (Bad Request) if the keyword has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/keywords")
    public ResponseEntity<KeywordDTO> createKeyword(@Valid @RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to save Keyword : {}", keywordDTO);
        if (keywordDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyword cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeywordDTO result = keywordService.save(keywordDTO);
        return ResponseEntity.created(new URI("/api/keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keywords : Updates an existing keyword.
     *
     * @param keywordDTO the keywordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keywordDTO,
     * or with status 400 (Bad Request) if the keywordDTO is not valid,
     * or with status 500 (Internal Server Error) if the keywordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/keywords")
    public ResponseEntity<KeywordDTO> updateKeyword(@Valid @RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to update Keyword : {}", keywordDTO);
        if (keywordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeywordDTO result = keywordService.save(keywordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keywordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keywords : get all the keywords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of keywords in body
     */
    @GetMapping("/keywords")
    public List<KeywordDTO> getAllKeywords() {
        log.debug("REST request to get all Keywords");
        return keywordService.findAll();
    }

    /**
     * GET  /keywords/:id : get the "id" keyword.
     *
     * @param id the id of the keywordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keywordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/keywords/{id}")
    public ResponseEntity<KeywordDTO> getKeyword(@PathVariable Long id) {
        log.debug("REST request to get Keyword : {}", id);
        Optional<KeywordDTO> keywordDTO = keywordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keywordDTO);
    }

    /**
     * DELETE  /keywords/:id : delete the "id" keyword.
     *
     * @param id the id of the keywordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/keywords/{id}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        log.debug("REST request to delete Keyword : {}", id);
        keywordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
