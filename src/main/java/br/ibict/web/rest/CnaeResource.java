package br.ibict.web.rest;
import br.ibict.service.CnaeService;
import br.ibict.web.rest.errors.BadRequestAlertException;
import br.ibict.web.rest.util.HeaderUtil;
import br.ibict.service.dto.CnaeDTO;
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
 * REST controller for managing Cnae.
 */
@RestController
@RequestMapping("/api")
public class CnaeResource {

    private final Logger log = LoggerFactory.getLogger(CnaeResource.class);

    private static final String ENTITY_NAME = "cnae";

    private final CnaeService cnaeService;

    public CnaeResource(CnaeService cnaeService) {
        this.cnaeService = cnaeService;
    }

    /**
     * POST  /cnaes : Create a new cnae.
     *
     * @param cnaeDTO the cnaeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cnaeDTO, or with status 400 (Bad Request) if the cnae has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cnaes")
    public ResponseEntity<CnaeDTO> createCnae(@Valid @RequestBody CnaeDTO cnaeDTO) throws URISyntaxException {
        log.debug("REST request to save Cnae : {}", cnaeDTO);
        if (cnaeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CnaeDTO result = cnaeService.save(cnaeDTO);
        return ResponseEntity.created(new URI("/api/cnaes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cnaes : Updates an existing cnae.
     *
     * @param cnaeDTO the cnaeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cnaeDTO,
     * or with status 400 (Bad Request) if the cnaeDTO is not valid,
     * or with status 500 (Internal Server Error) if the cnaeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cnaes")
    public ResponseEntity<CnaeDTO> updateCnae(@Valid @RequestBody CnaeDTO cnaeDTO) throws URISyntaxException {
        log.debug("REST request to update Cnae : {}", cnaeDTO);
        if (cnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CnaeDTO result = cnaeService.save(cnaeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cnaeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cnaes : get all the cnaes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cnaes in body
     */
    @GetMapping("/cnaes")
    public List<CnaeDTO> getAllCnaes() {
        log.debug("REST request to get all Cnaes");
        return cnaeService.findAll();
    }

    /**
     * GET  /cnaes/:id : get the "id" cnae.
     *
     * @param id the id of the cnaeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cnaeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cnaes/{id}")
    public ResponseEntity<CnaeDTO> getCnae(@PathVariable Long id) {
        log.debug("REST request to get Cnae : {}", id);
        Optional<CnaeDTO> cnaeDTO = cnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cnaeDTO);
    }

    /**
     * DELETE  /cnaes/:id : delete the "id" cnae.
     *
     * @param id the id of the cnaeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cnaes/{id}")
    public ResponseEntity<Void> deleteCnae(@PathVariable Long id) {
        log.debug("REST request to delete Cnae : {}", id);
        cnaeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
