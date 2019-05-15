package br.ibict.web.rest;

import br.ibict.SbrtApp;

import br.ibict.domain.Cnae;
import br.ibict.repository.CnaeRepository;
import br.ibict.service.CnaeService;
import br.ibict.service.dto.CnaeDTO;
import br.ibict.service.mapper.CnaeMapper;
import br.ibict.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static br.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CnaeResource REST controller.
 *
 * @see CnaeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbrtApp.class)
public class CnaeResourceIntTest {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String DEFAULT_COD = "AAAAAAAAAA";
    private static final String UPDATED_COD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CnaeRepository cnaeRepository;

    @Autowired
    private CnaeMapper cnaeMapper;

    @Autowired
    private CnaeService cnaeService;

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

    private MockMvc restCnaeMockMvc;

    private Cnae cnae;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CnaeResource cnaeResource = new CnaeResource(cnaeService);
        this.restCnaeMockMvc = MockMvcBuilders.standaloneSetup(cnaeResource)
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
    public static Cnae createEntity(EntityManager em) {
        Cnae cnae = new Cnae()
            .level(DEFAULT_LEVEL)
            .cod(DEFAULT_COD)
            .description(DEFAULT_DESCRIPTION);
        return cnae;
    }

    @Before
    public void initTest() {
        cnae = createEntity(em);
    }

    @Test
    @Transactional
    public void createCnae() throws Exception {
        int databaseSizeBeforeCreate = cnaeRepository.findAll().size();

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);
        restCnaeMockMvc.perform(post("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isCreated());

        // Validate the Cnae in the database
        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeCreate + 1);
        Cnae testCnae = cnaeList.get(cnaeList.size() - 1);
        assertThat(testCnae.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCnae.getCod()).isEqualTo(DEFAULT_COD);
        assertThat(testCnae.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCnaeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cnaeRepository.findAll().size();

        // Create the Cnae with an existing ID
        cnae.setId(1L);
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCnaeMockMvc.perform(post("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cnaeRepository.findAll().size();
        // set the field null
        cnae.setLevel(null);

        // Create the Cnae, which fails.
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        restCnaeMockMvc.perform(post("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodIsRequired() throws Exception {
        int databaseSizeBeforeTest = cnaeRepository.findAll().size();
        // set the field null
        cnae.setCod(null);

        // Create the Cnae, which fails.
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        restCnaeMockMvc.perform(post("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCnaes() throws Exception {
        // Initialize the database
        cnaeRepository.saveAndFlush(cnae);

        // Get all the cnaeList
        restCnaeMockMvc.perform(get("/api/cnaes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCnae() throws Exception {
        // Initialize the database
        cnaeRepository.saveAndFlush(cnae);

        // Get the cnae
        restCnaeMockMvc.perform(get("/api/cnaes/{id}", cnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cnae.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.cod").value(DEFAULT_COD.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCnae() throws Exception {
        // Get the cnae
        restCnaeMockMvc.perform(get("/api/cnaes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCnae() throws Exception {
        // Initialize the database
        cnaeRepository.saveAndFlush(cnae);

        int databaseSizeBeforeUpdate = cnaeRepository.findAll().size();

        // Update the cnae
        Cnae updatedCnae = cnaeRepository.findById(cnae.getId()).get();
        // Disconnect from session so that the updates on updatedCnae are not directly saved in db
        em.detach(updatedCnae);
        updatedCnae
            .level(UPDATED_LEVEL)
            .cod(UPDATED_COD)
            .description(UPDATED_DESCRIPTION);
        CnaeDTO cnaeDTO = cnaeMapper.toDto(updatedCnae);

        restCnaeMockMvc.perform(put("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isOk());

        // Validate the Cnae in the database
        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeUpdate);
        Cnae testCnae = cnaeList.get(cnaeList.size() - 1);
        assertThat(testCnae.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCnae.getCod()).isEqualTo(UPDATED_COD);
        assertThat(testCnae.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCnae() throws Exception {
        int databaseSizeBeforeUpdate = cnaeRepository.findAll().size();

        // Create the Cnae
        CnaeDTO cnaeDTO = cnaeMapper.toDto(cnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCnaeMockMvc.perform(put("/api/cnaes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cnae in the database
        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCnae() throws Exception {
        // Initialize the database
        cnaeRepository.saveAndFlush(cnae);

        int databaseSizeBeforeDelete = cnaeRepository.findAll().size();

        // Delete the cnae
        restCnaeMockMvc.perform(delete("/api/cnaes/{id}", cnae.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cnae> cnaeList = cnaeRepository.findAll();
        assertThat(cnaeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cnae.class);
        Cnae cnae1 = new Cnae();
        cnae1.setId(1L);
        Cnae cnae2 = new Cnae();
        cnae2.setId(cnae1.getId());
        assertThat(cnae1).isEqualTo(cnae2);
        cnae2.setId(2L);
        assertThat(cnae1).isNotEqualTo(cnae2);
        cnae1.setId(null);
        assertThat(cnae1).isNotEqualTo(cnae2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CnaeDTO.class);
        CnaeDTO cnaeDTO1 = new CnaeDTO();
        cnaeDTO1.setId(1L);
        CnaeDTO cnaeDTO2 = new CnaeDTO();
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
        cnaeDTO2.setId(cnaeDTO1.getId());
        assertThat(cnaeDTO1).isEqualTo(cnaeDTO2);
        cnaeDTO2.setId(2L);
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
        cnaeDTO1.setId(null);
        assertThat(cnaeDTO1).isNotEqualTo(cnaeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cnaeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cnaeMapper.fromId(null)).isNull();
    }
}
