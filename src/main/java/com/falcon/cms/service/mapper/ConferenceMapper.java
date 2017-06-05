package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.ConferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Conference and its DTO ConferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConferenceMapper extends EntityMapper <ConferenceDTO, Conference> {
    
    
    default Conference fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conference conference = new Conference();
        conference.setId(id);
        return conference;
    }
}
