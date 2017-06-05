package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.PaperDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paper and its DTO PaperDTO.
 */
@Mapper(componentModel = "spring", uses = {SectionMapper.class, })
public interface PaperMapper extends EntityMapper <PaperDTO, Paper> {

    @Mapping(source = "section.id", target = "sectionId")
    PaperDTO toDto(Paper paper); 

    @Mapping(source = "sectionId", target = "section")
    Paper toEntity(PaperDTO paperDTO); 
    default Paper fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paper paper = new Paper();
        paper.setId(id);
        return paper;
    }
}
