package com.falcon.cms.service.impl;

import com.falcon.cms.service.SectionService;
import com.falcon.cms.domain.Section;
import com.falcon.cms.repository.SectionRepository;
import com.falcon.cms.service.dto.SectionDTO;
import com.falcon.cms.service.mapper.SectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Section.
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService{

    private final Logger log = LoggerFactory.getLogger(SectionServiceImpl.class);

    private final SectionRepository sectionRepository;

    private final SectionMapper sectionMapper;

    public SectionServiceImpl(SectionRepository sectionRepository, SectionMapper sectionMapper) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
    }

    /**
     * Save a section.
     *
     * @param sectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SectionDTO save(SectionDTO sectionDTO) {
        log.debug("Request to save Section : {}", sectionDTO);
        Section section = sectionMapper.toEntity(sectionDTO);
        section = sectionRepository.save(section);
        return sectionMapper.toDto(section);
    }

    /**
     *  Get all the sections.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SectionDTO> findAll() {
        log.debug("Request to get all Sections");
        return sectionRepository.findAll().stream()
            .map(sectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one section by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SectionDTO findOne(Long id) {
        log.debug("Request to get Section : {}", id);
        Section section = sectionRepository.findOne(id);
        return sectionMapper.toDto(section);
    }

    /**
     *  Delete the  section by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Section : {}", id);
        sectionRepository.delete(id);
    }
}
