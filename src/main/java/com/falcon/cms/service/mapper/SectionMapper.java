package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.SectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Section and its DTO SectionDTO.
 */
@Mapper(componentModel = "spring", uses = {ConferenceMapper.class, })
public interface SectionMapper extends EntityMapper <SectionDTO, Section> {

    @Mapping(source = "conference.id", target = "conferenceId")
    SectionDTO toDto(Section section); 

    @Mapping(source = "conferenceId", target = "conference")
    Section toEntity(SectionDTO sectionDTO); 
    default Section fromId(Long id) {
        if (id == null) {
            return null;
        }
        Section section = new Section();
        section.setId(id);
        return section;
    }
}
