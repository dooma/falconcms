package com.falcon.cms.service.impl;

import com.falcon.cms.service.ConferenceService;
import com.falcon.cms.domain.Conference;
import com.falcon.cms.repository.ConferenceRepository;
import com.falcon.cms.service.dto.ConferenceDTO;
import com.falcon.cms.service.mapper.ConferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Conference.
 */
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService{

    private final Logger log = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    private final ConferenceRepository conferenceRepository;

    private final ConferenceMapper conferenceMapper;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceMapper conferenceMapper) {
        this.conferenceRepository = conferenceRepository;
        this.conferenceMapper = conferenceMapper;
    }

    /**
     * Save a conference.
     *
     * @param conferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConferenceDTO save(ConferenceDTO conferenceDTO) {
        log.debug("Request to save Conference : {}", conferenceDTO);
        Conference conference = conferenceMapper.toEntity(conferenceDTO);
        conference = conferenceRepository.save(conference);
        return conferenceMapper.toDto(conference);
    }

    /**
     *  Get all the conferences.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> findAll() {
        log.debug("Request to get all Conferences");
        return conferenceRepository.findAll().stream()
            .map(conferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one conference by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConferenceDTO findOne(Long id) {
        log.debug("Request to get Conference : {}", id);
        Conference conference = conferenceRepository.findOne(id);
        return conferenceMapper.toDto(conference);
    }

    /**
     *  Delete the  conference by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conference : {}", id);
        conferenceRepository.delete(id);
    }
}
