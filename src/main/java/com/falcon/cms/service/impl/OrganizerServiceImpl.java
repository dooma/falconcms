package com.falcon.cms.service.impl;

import com.falcon.cms.service.OrganizerService;
import com.falcon.cms.domain.Organizer;
import com.falcon.cms.repository.OrganizerRepository;
import com.falcon.cms.service.dto.OrganizerDTO;
import com.falcon.cms.service.mapper.OrganizerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Organizer.
 */
@Service
@Transactional
public class OrganizerServiceImpl implements OrganizerService{

    private final Logger log = LoggerFactory.getLogger(OrganizerServiceImpl.class);

    private final OrganizerRepository organizerRepository;

    private final OrganizerMapper organizerMapper;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository, OrganizerMapper organizerMapper) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
    }

    /**
     * Save a organizer.
     *
     * @param organizerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizerDTO save(OrganizerDTO organizerDTO) {
        log.debug("Request to save Organizer : {}", organizerDTO);
        Organizer organizer = organizerMapper.toEntity(organizerDTO);
        organizer = organizerRepository.save(organizer);
        return organizerMapper.toDto(organizer);
    }

    /**
     *  Get all the organizers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizerDTO> findAll() {
        log.debug("Request to get all Organizers");
        return organizerRepository.findAll().stream()
            .map(organizerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one organizer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizerDTO findOne(Long id) {
        log.debug("Request to get Organizer : {}", id);
        Organizer organizer = organizerRepository.findOne(id);
        return organizerMapper.toDto(organizer);
    }

    /**
     *  Delete the  organizer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organizer : {}", id);
        organizerRepository.delete(id);
    }
}
