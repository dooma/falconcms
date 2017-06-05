package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.PaperAuthorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaperAuthor and its DTO PaperAuthorDTO.
 */
@Mapper(componentModel = "spring", uses = {PaperMapper.class, AuthorMapper.class, })
public interface PaperAuthorMapper extends EntityMapper <PaperAuthorDTO, PaperAuthor> {

    @Mapping(source = "paper.id", target = "paperId")

    @Mapping(source = "author.id", target = "authorId")
    PaperAuthorDTO toDto(PaperAuthor paperAuthor); 

    @Mapping(source = "paperId", target = "paper")

    @Mapping(source = "authorId", target = "author")
    PaperAuthor toEntity(PaperAuthorDTO paperAuthorDTO); 
    default PaperAuthor fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaperAuthor paperAuthor = new PaperAuthor();
        paperAuthor.setId(id);
        return paperAuthor;
    }
}
