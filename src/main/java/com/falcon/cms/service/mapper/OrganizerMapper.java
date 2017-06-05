package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.OrganizerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organizer and its DTO OrganizerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ConferenceMapper.class, })
public interface OrganizerMapper extends EntityMapper <OrganizerDTO, Organizer> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "conference.id", target = "conferenceId")
    OrganizerDTO toDto(Organizer organizer); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "conferenceId", target = "conference")
    Organizer toEntity(OrganizerDTO organizerDTO); 
    default Organizer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organizer organizer = new Organizer();
        organizer.setId(id);
        return organizer;
    }
}
