package com.falcon.cms.web.rest;

import com.falcon.cms.FalconcmsApp;

import com.falcon.cms.domain.PaperAuthor;
import com.falcon.cms.repository.PaperAuthorRepository;
import com.falcon.cms.service.PaperAuthorService;
import com.falcon.cms.service.dto.PaperAuthorDTO;
import com.falcon.cms.service.mapper.PaperAuthorMapper;
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
 * Test class for the PaperAuthorResource REST controller.
 *
 * @see PaperAuthorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FalconcmsApp.class)
public class PaperAuthorResourceIntTest {

    @Autowired
    private PaperAuthorRepository paperAuthorRepository;

    @Autowired
    private PaperAuthorMapper paperAuthorMapper;

    @Autowired
    private PaperAuthorService paperAuthorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaperAuthorMockMvc;

    private PaperAuthor paperAuthor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaperAuthorResource paperAuthorResource = new PaperAuthorResource(paperAuthorService);
        this.restPaperAuthorMockMvc = MockMvcBuilders.standaloneSetup(paperAuthorResource)
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
    public static PaperAuthor createEntity(EntityManager em) {
        PaperAuthor paperAuthor = new PaperAuthor();
        return paperAuthor;
    }

    @Before
    public void initTest() {
        paperAuthor = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaperAuthor() throws Exception {
        int databaseSizeBeforeCreate = paperAuthorRepository.findAll().size();

        // Create the PaperAuthor
        PaperAuthorDTO paperAuthorDTO = paperAuthorMapper.toDto(paperAuthor);
        restPaperAuthorMockMvc.perform(post("/api/paper-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperAuthorDTO)))
            .andExpect(status().isCreated());

        // Validate the PaperAuthor in the database
        List<PaperAuthor> paperAuthorList = paperAuthorRepository.findAll();
        assertThat(paperAuthorList).hasSize(databaseSizeBeforeCreate + 1);
        PaperAuthor testPaperAuthor = paperAuthorList.get(paperAuthorList.size() - 1);
    }

    @Test
    @Transactional
    public void createPaperAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paperAuthorRepository.findAll().size();

        // Create the PaperAuthor with an existing ID
        paperAuthor.setId(1L);
        PaperAuthorDTO paperAuthorDTO = paperAuthorMapper.toDto(paperAuthor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaperAuthorMockMvc.perform(post("/api/paper-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperAuthorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaperAuthor> paperAuthorList = paperAuthorRepository.findAll();
        assertThat(paperAuthorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaperAuthors() throws Exception {
        // Initialize the database
        paperAuthorRepository.saveAndFlush(paperAuthor);

        // Get all the paperAuthorList
        restPaperAuthorMockMvc.perform(get("/api/paper-authors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paperAuthor.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPaperAuthor() throws Exception {
        // Initialize the database
        paperAuthorRepository.saveAndFlush(paperAuthor);

        // Get the paperAuthor
        restPaperAuthorMockMvc.perform(get("/api/paper-authors/{id}", paperAuthor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paperAuthor.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaperAuthor() throws Exception {
        // Get the paperAuthor
        restPaperAuthorMockMvc.perform(get("/api/paper-authors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaperAuthor() throws Exception {
        // Initialize the database
        paperAuthorRepository.saveAndFlush(paperAuthor);
        int databaseSizeBeforeUpdate = paperAuthorRepository.findAll().size();

        // Update the paperAuthor
        PaperAuthor updatedPaperAuthor = paperAuthorRepository.findOne(paperAuthor.getId());
        PaperAuthorDTO paperAuthorDTO = paperAuthorMapper.toDto(updatedPaperAuthor);

        restPaperAuthorMockMvc.perform(put("/api/paper-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperAuthorDTO)))
            .andExpect(status().isOk());

        // Validate the PaperAuthor in the database
        List<PaperAuthor> paperAuthorList = paperAuthorRepository.findAll();
        assertThat(paperAuthorList).hasSize(databaseSizeBeforeUpdate);
        PaperAuthor testPaperAuthor = paperAuthorList.get(paperAuthorList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPaperAuthor() throws Exception {
        int databaseSizeBeforeUpdate = paperAuthorRepository.findAll().size();

        // Create the PaperAuthor
        PaperAuthorDTO paperAuthorDTO = paperAuthorMapper.toDto(paperAuthor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaperAuthorMockMvc.perform(put("/api/paper-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paperAuthorDTO)))
            .andExpect(status().isCreated());

        // Validate the PaperAuthor in the database
        List<PaperAuthor> paperAuthorList = paperAuthorRepository.findAll();
        assertThat(paperAuthorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaperAuthor() throws Exception {
        // Initialize the database
        paperAuthorRepository.saveAndFlush(paperAuthor);
        int databaseSizeBeforeDelete = paperAuthorRepository.findAll().size();

        // Get the paperAuthor
        restPaperAuthorMockMvc.perform(delete("/api/paper-authors/{id}", paperAuthor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaperAuthor> paperAuthorList = paperAuthorRepository.findAll();
        assertThat(paperAuthorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaperAuthor.class);
        PaperAuthor paperAuthor1 = new PaperAuthor();
        paperAuthor1.setId(1L);
        PaperAuthor paperAuthor2 = new PaperAuthor();
        paperAuthor2.setId(paperAuthor1.getId());
        assertThat(paperAuthor1).isEqualTo(paperAuthor2);
        paperAuthor2.setId(2L);
        assertThat(paperAuthor1).isNotEqualTo(paperAuthor2);
        paperAuthor1.setId(null);
        assertThat(paperAuthor1).isNotEqualTo(paperAuthor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaperAuthorDTO.class);
        PaperAuthorDTO paperAuthorDTO1 = new PaperAuthorDTO();
        paperAuthorDTO1.setId(1L);
        PaperAuthorDTO paperAuthorDTO2 = new PaperAuthorDTO();
        assertThat(paperAuthorDTO1).isNotEqualTo(paperAuthorDTO2);
        paperAuthorDTO2.setId(paperAuthorDTO1.getId());
        assertThat(paperAuthorDTO1).isEqualTo(paperAuthorDTO2);
        paperAuthorDTO2.setId(2L);
        assertThat(paperAuthorDTO1).isNotEqualTo(paperAuthorDTO2);
        paperAuthorDTO1.setId(null);
        assertThat(paperAuthorDTO1).isNotEqualTo(paperAuthorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paperAuthorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paperAuthorMapper.fromId(null)).isNull();
    }
}
