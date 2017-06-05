package com.falcon.cms.web.rest;

import com.falcon.cms.FalconcmsApp;

import com.falcon.cms.domain.Conference;
import com.falcon.cms.repository.ConferenceRepository;
import com.falcon.cms.service.ConferenceService;
import com.falcon.cms.service.dto.ConferenceDTO;
import com.falcon.cms.service.mapper.ConferenceMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.falcon.cms.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConferenceResource REST controller.
 *
 * @see ConferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FalconcmsApp.class)
public class ConferenceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_STOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_STOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CALL_FOR_PAPERS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CALL_FOR_PAPERS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ConferenceMapper conferenceMapper;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConferenceMockMvc;

    private Conference conference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConferenceResource conferenceResource = new ConferenceResource(conferenceService);
        this.restConferenceMockMvc = MockMvcBuilders.standaloneSetup(conferenceResource)
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
    public static Conference createEntity(EntityManager em) {
        Conference conference = new Conference()
            .name(DEFAULT_NAME)
            .dateStart(DEFAULT_DATE_START)
            .dateStop(DEFAULT_DATE_STOP)
            .callForPapers(DEFAULT_CALL_FOR_PAPERS)
            .location(DEFAULT_LOCATION);
        return conference;
    }

    @Before
    public void initTest() {
        conference = createEntity(em);
    }

    @Test
    @Transactional
    public void createConference() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConference.getDateStart()).isEqualTo(DEFAULT_DATE_START);
        assertThat(testConference.getDateStop()).isEqualTo(DEFAULT_DATE_STOP);
        assertThat(testConference.getCallForPapers()).isEqualTo(DEFAULT_CALL_FOR_PAPERS);
        assertThat(testConference.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createConferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conferenceRepository.findAll().size();

        // Create the Conference with an existing ID
        conference.setId(1L);
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRepository.findAll().size();
        // set the field null
        conference.setName(null);

        // Create the Conference, which fails.
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRepository.findAll().size();
        // set the field null
        conference.setDateStart(null);

        // Create the Conference, which fails.
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRepository.findAll().size();
        // set the field null
        conference.setLocation(null);

        // Create the Conference, which fails.
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        restConferenceMockMvc.perform(post("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isBadRequest());

        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConferences() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get all the conferenceList
        restConferenceMockMvc.perform(get("/api/conferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateStart").value(hasItem(sameInstant(DEFAULT_DATE_START))))
            .andExpect(jsonPath("$.[*].dateStop").value(hasItem(sameInstant(DEFAULT_DATE_STOP))))
            .andExpect(jsonPath("$.[*].callForPapers").value(hasItem(sameInstant(DEFAULT_CALL_FOR_PAPERS))))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);

        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", conference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateStart").value(sameInstant(DEFAULT_DATE_START)))
            .andExpect(jsonPath("$.dateStop").value(sameInstant(DEFAULT_DATE_STOP)))
            .andExpect(jsonPath("$.callForPapers").value(sameInstant(DEFAULT_CALL_FOR_PAPERS)))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConference() throws Exception {
        // Get the conference
        restConferenceMockMvc.perform(get("/api/conferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);
        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Update the conference
        Conference updatedConference = conferenceRepository.findOne(conference.getId());
        updatedConference
            .name(UPDATED_NAME)
            .dateStart(UPDATED_DATE_START)
            .dateStop(UPDATED_DATE_STOP)
            .callForPapers(UPDATED_CALL_FOR_PAPERS)
            .location(UPDATED_LOCATION);
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(updatedConference);

        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isOk());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate);
        Conference testConference = conferenceList.get(conferenceList.size() - 1);
        assertThat(testConference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConference.getDateStart()).isEqualTo(UPDATED_DATE_START);
        assertThat(testConference.getDateStop()).isEqualTo(UPDATED_DATE_STOP);
        assertThat(testConference.getCallForPapers()).isEqualTo(UPDATED_CALL_FOR_PAPERS);
        assertThat(testConference.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingConference() throws Exception {
        int databaseSizeBeforeUpdate = conferenceRepository.findAll().size();

        // Create the Conference
        ConferenceDTO conferenceDTO = conferenceMapper.toDto(conference);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConferenceMockMvc.perform(put("/api/conferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Conference in the database
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConference() throws Exception {
        // Initialize the database
        conferenceRepository.saveAndFlush(conference);
        int databaseSizeBeforeDelete = conferenceRepository.findAll().size();

        // Get the conference
        restConferenceMockMvc.perform(delete("/api/conferences/{id}", conference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conference> conferenceList = conferenceRepository.findAll();
        assertThat(conferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conference.class);
        Conference conference1 = new Conference();
        conference1.setId(1L);
        Conference conference2 = new Conference();
        conference2.setId(conference1.getId());
        assertThat(conference1).isEqualTo(conference2);
        conference2.setId(2L);
        assertThat(conference1).isNotEqualTo(conference2);
        conference1.setId(null);
        assertThat(conference1).isNotEqualTo(conference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConferenceDTO.class);
        ConferenceDTO conferenceDTO1 = new ConferenceDTO();
        conferenceDTO1.setId(1L);
        ConferenceDTO conferenceDTO2 = new ConferenceDTO();
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO2.setId(conferenceDTO1.getId());
        assertThat(conferenceDTO1).isEqualTo(conferenceDTO2);
        conferenceDTO2.setId(2L);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
        conferenceDTO1.setId(null);
        assertThat(conferenceDTO1).isNotEqualTo(conferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conferenceMapper.fromId(null)).isNull();
    }
}
