package br.ibict.web.rest;

import br.ibict.domain.Answer;
import br.ibict.domain.User;
import br.ibict.security.AuthoritiesConstants;
import br.ibict.security.SecurityUtils;
import br.ibict.service.AnswerService;
import br.ibict.service.UserService;
import br.ibict.web.rest.errors.BadRequestAlertException;
import br.ibict.web.rest.util.HeaderUtil;
import br.ibict.web.rest.util.PaginationUtil;
import br.ibict.service.dto.AnswerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Answer.
 */
@RestController
@RequestMapping("/api")
public class AnswerResource {

    private final Logger log = LoggerFactory.getLogger(AnswerResource.class);

    private static final String ENTITY_NAME = "answer";

    private final AnswerService answerService;

    private final UserService userService;

    public AnswerResource(AnswerService answerService, UserService userService) {
        this.answerService = answerService;
        this.userService = userService;
    }

    /**
     * POST  /answers : Create a new answer.
     *
     * @param answerDTO the answerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new answerDTO, or with status 400 (Bad Request) if the answer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/answers")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.RESEARCHER + "\")")
    public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
        log.debug("REST request to save Answer : {}", answerDTO);

        assureAuthorizedOrFail(answerDTO.getLegalEntityId());

        if (answerDTO.getId() != null) {
            throw new BadRequestAlertException("A new answer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerDTO result = answerService.save(answerDTO);
        return ResponseEntity.created(new URI("/api/answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /answers : Updates an existing answer.
     *
     * @param answerDTO the answerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated answerDTO,
     * or with status 400 (Bad Request) if the answerDTO is not valid,
     * or with status 401 (Unauthorized) if the current user does not represent the old legalEntity,
     * or with status 500 (Internal Server Error) if the answerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/answers")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.RESEARCHER + "\")")
    public ResponseEntity<AnswerDTO> updateAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
        log.debug("REST request to update Answer : {}", answerDTO);
        if (answerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<AnswerDTO> answerOldOpt = answerService.findOne(answerDTO.getId());
        answerOldOpt.ifPresent(a -> this.assureAuthorizedOrFail(a.getLegalEntityId()));

        answerDTO.setDatePublished(Instant.now());
        answerDTO.setTimesSeen(new Integer(0));

        AnswerDTO result = answerService.save(answerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, answerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /answers : get all the answers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of answers in body
     */
    @GetMapping("/answers")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers(Pageable pageable) {
        log.debug("REST request to get a page of Answers");
        Page<AnswerDTO> page = answerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /answers/legalEntity/:legalEntityId : get all the answers by the id of the responsible legal entity.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of answers in body, 
     * or with status 401 (Unauthorized) if the current user does not represent the legalEntity
     */
    @GetMapping("/answers/legalEntity/{legalEntityId}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MEDIATOR + "\") or hasRole(\"" + 
            AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.RESEARCHER + "\")")
    public ResponseEntity<List<AnswerDTO>> getAllAnswersFromLegalEntity(Pageable pageable, @PathVariable Long legalEntityId) {
        log.debug("REST request to get a page of Answers from a legal entity");

        assureAuthorizedOrFail(legalEntityId);
            
        Page<AnswerDTO> page = answerService.findAllByLegalEntity(pageable, legalEntityId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /answers/:id : get the "id" answer.
     *
     * @param id the id of the answerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the answerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/answers/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
        log.debug("REST request to get Answer : {}", id);
        Optional<AnswerDTO> answerDTO = answerService.findOne(id);
        answerDTO = answerService.incrementAnswerSeen(answerDTO);
        return ResponseUtil.wrapOrNotFound(answerDTO);
    }

    /**
     * DELETE  /answers/:id : delete the "id" answer.
     *
     * @param id the id of the answerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/answers/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")" )
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        log.debug("REST request to delete Answer : {}", id);
        answerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Checks if the current user is authorized to access resources of the legal entity of :legalEntityId
     * Throw a 401 ResponseStatusException otherwise.
     * @param legalEntityId
     */
    private void assureAuthorizedOrFail(Long legalEntityId) {
            if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) return;

            Optional<String> usernameOpt = SecurityUtils.getCurrentUserLogin();
            if(!usernameOpt.isPresent()) 
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");
            
            String username = usernameOpt.get();
            Optional<User> userOpt = userService.getUserWithAuthoritiesByLogin(username);
            if(!userOpt.isPresent()) 
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");

            User user = userOpt.get();
            if(user.getPerson() == null || !user.getPerson().representsLegalEntity(legalEntityId)) 
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this resource");
    }
}
