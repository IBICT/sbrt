package br.ibict.web.rest;
import br.ibict.domain.User;
import br.ibict.security.AuthoritiesConstants;
import br.ibict.security.SecurityUtils;
import br.ibict.service.AnswerService;
import br.ibict.service.UserService;
import br.ibict.web.rest.errors.BadRequestAlertException;
import br.ibict.web.rest.util.HeaderUtil;
import br.ibict.web.rest.util.PaginationUtil;
import br.ibict.service.dto.AnswerDTO;
import br.ibict.service.dto.AnswerCriteria;
import br.ibict.service.AnswerQueryService;
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

    private final AnswerQueryService answerQueryService;

    public AnswerResource(AnswerService answerService, UserService userService, AnswerQueryService answerQueryService) {
        this.answerService = answerService;
        this.userService = userService;
        this.answerQueryService = answerQueryService;
    }

    /**
     * POST  /answers : Create a new answer.
     *
     * @param answerDTO the answerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new answerDTO, or with status 400 (Bad Request) if the answer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/answers")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.RESEARCHER + "\") " + " OR hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) throws URISyntaxException {
        log.debug("REST request to save Answer : {}", answerDTO);
        if(this.answerService.findByQuestion(answerDTO.getQuestionId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There already is an answer to this question.");
        }

        assureAuthorizedOrFail(answerDTO.getLegalEntityId());

        answerDTO.setDatePublished(Instant.now());
        answerDTO.setTimesSeen(new Integer(0));

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
        if(answerOldOpt.isPresent()) {
            AnswerDTO answerDTOOld = answerOldOpt.get();
            this.assureAuthorizedOrFail(answerDTOOld.getLegalEntityId());
            answerDTO.setDatePublished(answerDTOOld.getDatePublished());
            answerDTO.setTimesSeen(answerDTOOld.getTimesSeen());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.http." + HttpStatus.NOT_FOUND.toString());
        }
        AnswerDTO result = answerService.save(answerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, answerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /answers : get all the answers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of answers in body
     */
    @GetMapping("/answers")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers(AnswerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Answers by criteria: {}", criteria);
        Page<AnswerDTO> page = answerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /answers/count : count all the answers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/answers/count")
    public ResponseEntity<Long> countAnswers(AnswerCriteria criteria) {
        log.debug("REST request to count Answers by criteria: {}", criteria);
        return ResponseEntity.ok().body(answerQueryService.countByCriteria(criteria));
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

    @PostMapping("/answers/{answerId}/rate")
    public ResponseEntity<Void> rateAnswer(@RequestBody Short rating, @PathVariable Long answerId) {
        Long userId = SecurityUtils.getCurrentUserID().orElseThrow( () ->
            new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource"));
        this.answerService.rateAnswer(rating, userId, answerId);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks if the current user is authorized to access resources of the legal entity of :legalEntityId
     * Throw a 401 ResponseStatusException otherwise.
     * @param legalEntityId
     */
    private void assureAuthorizedOrFail(Long legalEntityId) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            return;

        Optional<String> usernameOpt = SecurityUtils.getCurrentUserLogin();
        if (!usernameOpt.isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");

        String username = usernameOpt.get();
        Optional<User> userOpt = userService.getUserWithAuthoritiesByLogin(username);
        if (!userOpt.isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");

        User user = userOpt.get();
        if (user.getPerson() == null || !user.getPerson().representsLegalEntity(legalEntityId))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this resource");
    }
}
