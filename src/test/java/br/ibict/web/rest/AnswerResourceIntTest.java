package br.ibict.web.rest;

import br.ibict.SbrtApp;

import br.ibict.domain.Answer;
import br.ibict.domain.User;
import br.ibict.domain.Question;
import br.ibict.domain.LegalEntity;
import br.ibict.domain.Cnae;
import br.ibict.domain.Keyword;
import br.ibict.repository.AnswerRepository;
import br.ibict.service.AnswerService;
import br.ibict.service.UserService;
import br.ibict.service.dto.AnswerDTO;
import br.ibict.service.mapper.AnswerMapper;
import br.ibict.web.rest.errors.ExceptionTranslator;
import br.ibict.service.dto.AnswerCriteria;
import br.ibict.service.AnswerQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnswerResource REST controller.
 *
 * @see AnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbrtApp.class)
public class AnswerResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PUBLISHED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PUBLISHED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMES_SEEN = 0;
    private static final Integer UPDATED_TIMES_SEEN = 1;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerQueryService answerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnswerMockMvc;

    private Answer answer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerResource answerResource = new AnswerResource(answerService, userService, answerQueryService);
        this.restAnswerMockMvc = MockMvcBuilders.standaloneSetup(answerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .datePublished(DEFAULT_DATE_PUBLISHED)
            .content(DEFAULT_CONTENT)
            .timesSeen(DEFAULT_TIMES_SEEN);
        return answer;
    }

    @Before
    public void initTest() {
        answer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswer() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isCreated());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate + 1);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnswer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAnswer.getDatePublished()).isEqualTo(DEFAULT_DATE_PUBLISHED);
        assertThat(testAnswer.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAnswer.getTimesSeen()).isEqualTo(DEFAULT_TIMES_SEEN);
    }

    @Test
    @Transactional
    public void createAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // Create the Answer with an existing ID
        answer.setId(1L);
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setTitle(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePublishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setDatePublished(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnswers() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].datePublished").value(hasItem(DEFAULT_DATE_PUBLISHED.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].timesSeen").value(hasItem(DEFAULT_TIMES_SEEN)));
    }

    @Test
    @Transactional
    public void getAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", answer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(answer.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.datePublished").value(DEFAULT_DATE_PUBLISHED.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.timesSeen").value(DEFAULT_TIMES_SEEN));
    }

    @Test
    @Transactional
    public void getAllAnswersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where title equals to DEFAULT_TITLE
        defaultAnswerShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the answerList where title equals to UPDATED_TITLE
        defaultAnswerShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnswersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultAnswerShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the answerList where title equals to UPDATED_TITLE
        defaultAnswerShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAnswersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where title is not null
        defaultAnswerShouldBeFound("title.specified=true");

        // Get all the answerList where title is null
        defaultAnswerShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description equals to DEFAULT_DESCRIPTION
        defaultAnswerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the answerList where description equals to UPDATED_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAnswerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the answerList where description equals to UPDATED_DESCRIPTION
        defaultAnswerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAnswersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where description is not null
        defaultAnswerShouldBeFound("description.specified=true");

        // Get all the answerList where description is null
        defaultAnswerShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByDatePublishedIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where datePublished equals to DEFAULT_DATE_PUBLISHED
        defaultAnswerShouldBeFound("datePublished.equals=" + DEFAULT_DATE_PUBLISHED);

        // Get all the answerList where datePublished equals to UPDATED_DATE_PUBLISHED
        defaultAnswerShouldNotBeFound("datePublished.equals=" + UPDATED_DATE_PUBLISHED);
    }

    @Test
    @Transactional
    public void getAllAnswersByDatePublishedIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where datePublished in DEFAULT_DATE_PUBLISHED or UPDATED_DATE_PUBLISHED
        defaultAnswerShouldBeFound("datePublished.in=" + DEFAULT_DATE_PUBLISHED + "," + UPDATED_DATE_PUBLISHED);

        // Get all the answerList where datePublished equals to UPDATED_DATE_PUBLISHED
        defaultAnswerShouldNotBeFound("datePublished.in=" + UPDATED_DATE_PUBLISHED);
    }

    @Test
    @Transactional
    public void getAllAnswersByDatePublishedIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where datePublished is not null
        defaultAnswerShouldBeFound("datePublished.specified=true");

        // Get all the answerList where datePublished is null
        defaultAnswerShouldNotBeFound("datePublished.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByTimesSeenIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where timesSeen equals to DEFAULT_TIMES_SEEN
        defaultAnswerShouldBeFound("timesSeen.equals=" + DEFAULT_TIMES_SEEN);

        // Get all the answerList where timesSeen equals to UPDATED_TIMES_SEEN
        defaultAnswerShouldNotBeFound("timesSeen.equals=" + UPDATED_TIMES_SEEN);
    }

    @Test
    @Transactional
    public void getAllAnswersByTimesSeenIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where timesSeen in DEFAULT_TIMES_SEEN or UPDATED_TIMES_SEEN
        defaultAnswerShouldBeFound("timesSeen.in=" + DEFAULT_TIMES_SEEN + "," + UPDATED_TIMES_SEEN);

        // Get all the answerList where timesSeen equals to UPDATED_TIMES_SEEN
        defaultAnswerShouldNotBeFound("timesSeen.in=" + UPDATED_TIMES_SEEN);
    }

    @Test
    @Transactional
    public void getAllAnswersByTimesSeenIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where timesSeen is not null
        defaultAnswerShouldBeFound("timesSeen.specified=true");

        // Get all the answerList where timesSeen is null
        defaultAnswerShouldNotBeFound("timesSeen.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByTimesSeenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where timesSeen greater than or equals to DEFAULT_TIMES_SEEN
        defaultAnswerShouldBeFound("timesSeen.greaterOrEqualThan=" + DEFAULT_TIMES_SEEN);

        // Get all the answerList where timesSeen greater than or equals to UPDATED_TIMES_SEEN
        defaultAnswerShouldNotBeFound("timesSeen.greaterOrEqualThan=" + UPDATED_TIMES_SEEN);
    }

    @Test
    @Transactional
    public void getAllAnswersByTimesSeenIsLessThanSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where timesSeen less than or equals to DEFAULT_TIMES_SEEN
        defaultAnswerShouldNotBeFound("timesSeen.lessThan=" + DEFAULT_TIMES_SEEN);

        // Get all the answerList where timesSeen less than or equals to UPDATED_TIMES_SEEN
        defaultAnswerShouldBeFound("timesSeen.lessThan=" + UPDATED_TIMES_SEEN);
    }


    @Test
    @Transactional
    public void getAllAnswersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        answer.setUser(user);
        answerRepository.saveAndFlush(answer);
        Long userId = user.getId();

        // Get all the answerList where user equals to userId
        defaultAnswerShouldBeFound("userId.equals=" + userId);

        // Get all the answerList where user equals to userId + 1
        defaultAnswerShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        Question question = QuestionResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        answer.setQuestion(question);
        answerRepository.saveAndFlush(answer);
        Long questionId = question.getId();

        // Get all the answerList where question equals to questionId
        defaultAnswerShouldBeFound("questionId.equals=" + questionId);

        // Get all the answerList where question equals to questionId + 1
        defaultAnswerShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByLegalEntityIsEqualToSomething() throws Exception {
        // Initialize the database
        LegalEntity legalEntity = LegalEntityResourceIntTest.createEntity(em);
        em.persist(legalEntity);
        em.flush();
        answer.setLegalEntity(legalEntity);
        answerRepository.saveAndFlush(answer);
        Long legalEntityId = legalEntity.getId();

        // Get all the answerList where legalEntity equals to legalEntityId
        defaultAnswerShouldBeFound("legalEntityId.equals=" + legalEntityId);

        // Get all the answerList where legalEntity equals to legalEntityId + 1
        defaultAnswerShouldNotBeFound("legalEntityId.equals=" + (legalEntityId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByCnaeIsEqualToSomething() throws Exception {
        // Initialize the database
        Cnae cnae = CnaeResourceIntTest.createEntity(em);
        em.persist(cnae);
        em.flush();
        answer.setCnae(cnae);
        answerRepository.saveAndFlush(answer);
        Long cnaeId = cnae.getId();

        // Get all the answerList where cnae equals to cnaeId
        defaultAnswerShouldBeFound("cnaeId.equals=" + cnaeId);

        // Get all the answerList where cnae equals to cnaeId + 1
        defaultAnswerShouldNotBeFound("cnaeId.equals=" + (cnaeId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByKeywordIsEqualToSomething() throws Exception {
        // Initialize the database
        Keyword keyword = KeywordResourceIntTest.createEntity(em);
        em.persist(keyword);
        em.flush();
        answer.addKeyword(keyword);
        answerRepository.saveAndFlush(answer);
        Long keywordId = keyword.getId();

        // Get all the answerList where keyword equals to keywordId
        defaultAnswerShouldBeFound("keywordId.equals=" + keywordId);

        // Get all the answerList where keyword equals to keywordId + 1
        defaultAnswerShouldNotBeFound("keywordId.equals=" + (keywordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAnswerShouldBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].datePublished").value(hasItem(DEFAULT_DATE_PUBLISHED.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].timesSeen").value(hasItem(DEFAULT_TIMES_SEEN)));

        // Check, that the count call also returns 1
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAnswerShouldNotBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnswer() throws Exception {
        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer
        Answer updatedAnswer = answerRepository.findById(answer.getId()).get();
        // Disconnect from session so that the updates on updatedAnswer are not directly saved in db
        em.detach(updatedAnswer);
        updatedAnswer
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .datePublished(UPDATED_DATE_PUBLISHED)
            .content(UPDATED_CONTENT)
            .timesSeen(UPDATED_TIMES_SEEN);
        AnswerDTO answerDTO = answerMapper.toDto(updatedAnswer);

        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnswer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAnswer.getDatePublished()).isEqualTo(UPDATED_DATE_PUBLISHED);
        assertThat(testAnswer.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAnswer.getTimesSeen()).isEqualTo(UPDATED_TIMES_SEEN);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeDelete = answerRepository.findAll().size();

        // Delete the answer
        restAnswerMockMvc.perform(delete("/api/answers/{id}", answer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Answer.class);
        Answer answer1 = new Answer();
        answer1.setId(1L);
        Answer answer2 = new Answer();
        answer2.setId(answer1.getId());
        assertThat(answer1).isEqualTo(answer2);
        answer2.setId(2L);
        assertThat(answer1).isNotEqualTo(answer2);
        answer1.setId(null);
        assertThat(answer1).isNotEqualTo(answer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerDTO.class);
        AnswerDTO answerDTO1 = new AnswerDTO();
        answerDTO1.setId(1L);
        AnswerDTO answerDTO2 = new AnswerDTO();
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
        answerDTO2.setId(answerDTO1.getId());
        assertThat(answerDTO1).isEqualTo(answerDTO2);
        answerDTO2.setId(2L);
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
        answerDTO1.setId(null);
        assertThat(answerDTO1).isNotEqualTo(answerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerMapper.fromId(null)).isNull();
    }
}
