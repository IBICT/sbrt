package br.ibict.web.rest;

import br.ibict.domain.User;
import br.ibict.security.AuthoritiesConstants;
import br.ibict.security.SecurityUtils;
import br.ibict.service.QuestionService;
import br.ibict.service.UserService;
import br.ibict.web.rest.errors.BadRequestAlertException;
import br.ibict.web.rest.errors.InvalidUFException;
import br.ibict.web.rest.util.HeaderUtil;
import br.ibict.web.rest.util.InstitutionEnum;
import br.ibict.web.rest.util.PaginationUtil;
import br.ibict.service.dto.QuestionDTO;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Question.
 */
@RestController
@RequestMapping("/api")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private static final String ENTITY_NAME = "question";

    private final QuestionService questionService;

    private final UserService userService;

    public QuestionResource(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    /**
     * POST  /questions : Create a new question.
     *
     * @param questionDTO the questionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionDTO, or with status 400 (Bad Request) if the question has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * TODO: Recover UF from user
     */
    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to save Question : {}", questionDTO);
        if (questionDTO.getId() != null) {
            throw new BadRequestAlertException("A new question cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            questionDTO = setRouteQuestion("DF", questionDTO);
        } catch (InvalidUFException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User has invalid UF");
        }
        QuestionDTO result = questionService.save(questionDTO);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questions : Updates an existing question.
     *
     * @param questionDTO the questionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionDTO,
     * or with status 400 (Bad Request) if the questionDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions")
    public ResponseEntity<QuestionDTO> updateQuestion(@Valid @RequestBody QuestionDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to update Question : {}", questionDTO);
        if (questionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionDTO result = questionService.save(questionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questions : get all the questions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questions in body
     */
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(Pageable pageable) {
        log.debug("REST request to get a page of Questions");
        Page<QuestionDTO> page = questionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /questions/:id : get the "id" question.
     *
     * @param id the id of the questionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long id) {
        log.debug("REST request to get Question : {}", id);
        Optional<QuestionDTO> questionDTO = questionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionDTO);
    }

    /**
     * DELETE  /questions/:id : delete the "id" question.
     *
     * @param id the id of the questionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.debug("REST request to delete Question : {}", id);
        questionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /questions/:id/handler : change the handler of the "id" question.
     *
     * @param id the id of the questionDTO to change
     * @param handlerId the id of the legalEntity to which the question is sent
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/questions/{id}/handler")
    public ResponseEntity<QuestionDTO> changeQuestionHandler(@PathVariable Long id, @RequestBody Long handlerId) {
        log.debug("REST request to change Question handler : {}", id);

        QuestionDTO questionDTO = questionService.changeHandler(id, handlerId);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionDTO.getId().toString()))
            .body(questionDTO);
    }

    /**
     * GET  /questions/legalEntity/:legalEntityId : get all the answers by the id of the responsible legal entity.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of answers in body, 
     * or with status 401 (Unauthorized) if the current user does not represent the legalEntity
     */
    @GetMapping("/questions/legalEntity/{legalEntityId}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MEDIATOR + "\") or hasRole(\"" + 
            AuthoritiesConstants.ADMIN + "\") or hasRole(\"" + AuthoritiesConstants.RESEARCHER + "\")")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsForLegalEntity(Pageable pageable, @PathVariable Long legalEntityId) {
        log.debug("REST request to get a page of Questions for a legal entity");

        assureAuthorizedOrFail(legalEntityId);
            
        Page<QuestionDTO> page = questionService.findAllByLegalEntity(pageable, legalEntityId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Checks if the current user is authorized to access resources of the legal entity of :legalEntityId
     * Throw a 401 ResponseStatusException otherwise.
     * @param legalEntityId
     */
    private void assureAuthorizedOrFail(Long legalEntityId) {
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) return;

        Optional<String> usernameOpt = SecurityUtils.getCurrentUserLogin();
        if(!usernameOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");
        }
        
        String username = usernameOpt.get();
        Optional<User> userOpt = userService.getUserWithAuthoritiesByLogin(username);
        if(!userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to access this resource");
        }

        User user = userOpt.get();
        if(user.getPerson() == null || !user.getPerson().representsLegalEntity(legalEntityId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this resource");
        }
    }

    // TODO handle errors, add other institutions
    private QuestionDTO setRouteQuestion(String uf, QuestionDTO questionDTO) throws InvalidUFException {
        switch (uf) {
            case "DF":
                questionDTO.setLegalEntityId(InstitutionEnum.UNB);
                return questionDTO;
            default:
                throw new InvalidUFException();
        }
    }
}
