package com.falcon.cms.web.rest;

import com.falcon.cms.FalconcmsApp;

import com.falcon.cms.domain.Paper;
import com.falcon.cms.repository.PaperRepository;
import com.falcon.cms.service.PaperService;
import com.falcon.cms.service.dto.PaperDTO;
import com.falcon.cms.service.mapper.PaperMapper;
import com.falcon.cms.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaperResource REST controller.
 *
 * @see PaperResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FalconcmsApp.class)
public class PaperResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_META_INFO = "AAAAAAAAAA";
    private static final String UPDATED_META_INFO = "BBBBBBBBBB";

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperService paperService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaperMockMvc;

    private Paper paper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaperResource paperResource = new PaperResource(paperService);
        this.restPaperMockMvc = MockMvcBuilders.standaloneSetup(paperResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paper createEntity(EntityManager em) {
        Paper paper = new Paper()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .keywords(DEFAULT_KEYWORDS)
            .metaInfo(DEFAULT_META_INFO);
        return paper;
    }

    @Before
    public void initTest() {
        paper = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaper() throws Exception {
        int databaseSizeBeforeCreate = paperRepository.findAll().size();

        // Create the Paper
        PaperDTO paperDTO = paperMapper.toDto(paper);
        restPaperMockMvc.perform(post("/api/papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperDTO)))
            .andExpect(status().isCreated());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeCreate + 1);
        Paper testPaper = paperList.get(paperList.size() - 1);
        assertThat(testPaper.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaper.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaper.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testPaper.getMetaInfo()).isEqualTo(DEFAULT_META_INFO);
    }

    @Test
    @Transactional
    public void createPaperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paperRepository.findAll().size();

        // Create the Paper with an existing ID
        paper.setId(1L);
        PaperDTO paperDTO = paperMapper.toDto(paper);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaperMockMvc.perform(post("/api/papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paperRepository.findAll().size();
        // set the field null
        paper.setName(null);

        // Create the Paper, which fails.
        PaperDTO paperDTO = paperMapper.toDto(paper);

        restPaperMockMvc.perform(post("/api/papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperDTO)))
            .andExpect(status().isBadRequest());

        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPapers() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        // Get all the paperList
        restPaperMockMvc.perform(get("/api/papers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paper.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].metaInfo").value(hasItem(DEFAULT_META_INFO.toString())));
    }

    @Test
    @Transactional
    public void getPaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);

        // Get the paper
        restPaperMockMvc.perform(get("/api/papers/{id}", paper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paper.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.metaInfo").value(DEFAULT_META_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaper() throws Exception {
        // Get the paper
        restPaperMockMvc.perform(get("/api/papers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);
        int databaseSizeBeforeUpdate = paperRepository.findAll().size();

        // Update the paper
        Paper updatedPaper = paperRepository.findOne(paper.getId());
        updatedPaper
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .keywords(UPDATED_KEYWORDS)
            .metaInfo(UPDATED_META_INFO);
        PaperDTO paperDTO = paperMapper.toDto(updatedPaper);

        restPaperMockMvc.perform(put("/api/papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperDTO)))
            .andExpect(status().isOk());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeUpdate);
        Paper testPaper = paperList.get(paperList.size() - 1);
        assertThat(testPaper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaper.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaper.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testPaper.getMetaInfo()).isEqualTo(UPDATED_META_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaper() throws Exception {
        int databaseSizeBeforeUpdate = paperRepository.findAll().size();

        // Create the Paper
        PaperDTO paperDTO = paperMapper.toDto(paper);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaperMockMvc.perform(put("/api/papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperDTO)))
            .andExpect(status().isCreated());

        // Validate the Paper in the database
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaper() throws Exception {
        // Initialize the database
        paperRepository.saveAndFlush(paper);
        int databaseSizeBeforeDelete = paperRepository.findAll().size();

        // Get the paper
        restPaperMockMvc.perform(delete("/api/papers/{id}", paper.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paper> paperList = paperRepository.findAll();
        assertThat(paperList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paper.class);
        Paper paper1 = new Paper();
        paper1.setId(1L);
        Paper paper2 = new Paper();
        paper2.setId(paper1.getId());
        assertThat(paper1).isEqualTo(paper2);
        paper2.setId(2L);
        assertThat(paper1).isNotEqualTo(paper2);
        paper1.setId(null);
        assertThat(paper1).isNotEqualTo(paper2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaperDTO.class);
        PaperDTO paperDTO1 = new PaperDTO();
        paperDTO1.setId(1L);
        PaperDTO paperDTO2 = new PaperDTO();
        assertThat(paperDTO1).isNotEqualTo(paperDTO2);
        paperDTO2.setId(paperDTO1.getId());
        assertThat(paperDTO1).isEqualTo(paperDTO2);
        paperDTO2.setId(2L);
        assertThat(paperDTO1).isNotEqualTo(paperDTO2);
        paperDTO1.setId(null);
        assertThat(paperDTO1).isNotEqualTo(paperDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paperMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paperMapper.fromId(null)).isNull();
    }
}
