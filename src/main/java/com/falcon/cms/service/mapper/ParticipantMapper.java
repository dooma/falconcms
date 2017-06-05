package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.ParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Participant and its DTO ParticipantDTO.
 */
@Mapper(componentModel = "spring", uses = {SectionMapper.class, UserMapper.class, })
public interface ParticipantMapper extends EntityMapper <ParticipantDTO, Participant> {

    @Mapping(source = "section.id", target = "sectionId")

    @Mapping(source = "user.id", target = "userId")
    ParticipantDTO toDto(Participant participant); 

    @Mapping(source = "sectionId", target = "section")

    @Mapping(source = "userId", target = "user")
    Participant toEntity(ParticipantDTO participantDTO); 
    default Participant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Participant participant = new Participant();
        participant.setId(id);
        return participant;
    }
}
