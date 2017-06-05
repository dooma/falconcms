package com.falcon.cms.web.rest;

import com.falcon.cms.FalconcmsApp;

import com.falcon.cms.domain.Organizer;
import com.falcon.cms.repository.OrganizerRepository;
import com.falcon.cms.service.OrganizerService;
import com.falcon.cms.service.dto.OrganizerDTO;
import com.falcon.cms.service.mapper.OrganizerMapper;
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
 * Test class for the OrganizerResource REST controller.
 *
 * @see OrganizerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FalconcmsApp.class)
public class OrganizerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AFFILIATION = "AAAAAAAAAA";
    private static final String UPDATED_AFFILIATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private OrganizerMapper organizerMapper;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganizerMockMvc;

    private Organizer organizer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganizerResource organizerResource = new OrganizerResource(organizerService);
        this.restOrganizerMockMvc = MockMvcBuilders.standaloneSetup(organizerResource)
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
    public static Organizer createEntity(EntityManager em) {
        Organizer organizer = new Organizer()
            .name(DEFAULT_NAME)
            .affiliation(DEFAULT_AFFILIATION)
            .email(DEFAULT_EMAIL)
            .web(DEFAULT_WEB);
        return organizer;
    }

    @Before
    public void initTest() {
        organizer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizer() throws Exception {
        int databaseSizeBeforeCreate = organizerRepository.findAll().size();

        // Create the Organizer
        OrganizerDTO organizerDTO = organizerMapper.toDto(organizer);
        restOrganizerMockMvc.perform(post("/api/organizers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizerDTO)))
            .andExpect(status().isCreated());

        // Validate the Organizer in the database
        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeCreate + 1);
        Organizer testOrganizer = organizerList.get(organizerList.size() - 1);
        assertThat(testOrganizer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganizer.getAffiliation()).isEqualTo(DEFAULT_AFFILIATION);
        assertThat(testOrganizer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganizer.getWeb()).isEqualTo(DEFAULT_WEB);
    }

    @Test
    @Transactional
    public void createOrganizerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizerRepository.findAll().size();

        // Create the Organizer with an existing ID
        organizer.setId(1L);
        OrganizerDTO organizerDTO = organizerMapper.toDto(organizer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizerMockMvc.perform(post("/api/organizers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizerRepository.findAll().size();
        // set the field null
        organizer.setName(null);

        // Create the Organizer, which fails.
        OrganizerDTO organizerDTO = organizerMapper.toDto(organizer);

        restOrganizerMockMvc.perform(post("/api/organizers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizerDTO)))
            .andExpect(status().isBadRequest());

        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganizers() throws Exception {
        // Initialize the database
        organizerRepository.saveAndFlush(organizer);

        // Get all the organizerList
        restOrganizerMockMvc.perform(get("/api/organizers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].affiliation").value(hasItem(DEFAULT_AFFILIATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())));
    }

    @Test
    @Transactional
    public void getOrganizer() throws Exception {
        // Initialize the database
        organizerRepository.saveAndFlush(organizer);

        // Get the organizer
        restOrganizerMockMvc.perform(get("/api/organizers/{id}", organizer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.affiliation").value(DEFAULT_AFFILIATION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizer() throws Exception {
        // Get the organizer
        restOrganizerMockMvc.perform(get("/api/organizers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizer() throws Exception {
        // Initialize the database
        organizerRepository.saveAndFlush(organizer);
        int databaseSizeBeforeUpdate = organizerRepository.findAll().size();

        // Update the organizer
        Organizer updatedOrganizer = organizerRepository.findOne(organizer.getId());
        updatedOrganizer
            .name(UPDATED_NAME)
            .affiliation(UPDATED_AFFILIATION)
            .email(UPDATED_EMAIL)
            .web(UPDATED_WEB);
        OrganizerDTO organizerDTO = organizerMapper.toDto(updatedOrganizer);

        restOrganizerMockMvc.perform(put("/api/organizers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizerDTO)))
            .andExpect(status().isOk());

        // Validate the Organizer in the database
        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeUpdate);
        Organizer testOrganizer = organizerList.get(organizerList.size() - 1);
        assertThat(testOrganizer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganizer.getAffiliation()).isEqualTo(UPDATED_AFFILIATION);
        assertThat(testOrganizer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganizer.getWeb()).isEqualTo(UPDATED_WEB);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizer() throws Exception {
        int databaseSizeBeforeUpdate = organizerRepository.findAll().size();

        // Create the Organizer
        OrganizerDTO organizerDTO = organizerMapper.toDto(organizer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganizerMockMvc.perform(put("/api/organizers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizerDTO)))
            .andExpect(status().isCreated());

        // Validate the Organizer in the database
        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganizer() throws Exception {
        // Initialize the database
        organizerRepository.saveAndFlush(organizer);
        int databaseSizeBeforeDelete = organizerRepository.findAll().size();

        // Get the organizer
        restOrganizerMockMvc.perform(delete("/api/organizers/{id}", organizer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organizer> organizerList = organizerRepository.findAll();
        assertThat(organizerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organizer.class);
        Organizer organizer1 = new Organizer();
        organizer1.setId(1L);
        Organizer organizer2 = new Organizer();
        organizer2.setId(organizer1.getId());
        assertThat(organizer1).isEqualTo(organizer2);
        organizer2.setId(2L);
        assertThat(organizer1).isNotEqualTo(organizer2);
        organizer1.setId(null);
        assertThat(organizer1).isNotEqualTo(organizer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizerDTO.class);
        OrganizerDTO organizerDTO1 = new OrganizerDTO();
        organizerDTO1.setId(1L);
        OrganizerDTO organizerDTO2 = new OrganizerDTO();
        assertThat(organizerDTO1).isNotEqualTo(organizerDTO2);
        organizerDTO2.setId(organizerDTO1.getId());
        assertThat(organizerDTO1).isEqualTo(organizerDTO2);
        organizerDTO2.setId(2L);
        assertThat(organizerDTO1).isNotEqualTo(organizerDTO2);
        organizerDTO1.setId(null);
        assertThat(organizerDTO1).isNotEqualTo(organizerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizerMapper.fromId(null)).isNull();
    }
}
