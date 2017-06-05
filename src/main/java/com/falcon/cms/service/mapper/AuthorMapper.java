package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.AuthorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Author and its DTO AuthorDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface AuthorMapper extends EntityMapper <AuthorDTO, Author> {

    @Mapping(source = "user.id", target = "userId")
    AuthorDTO toDto(Author author); 

    @Mapping(source = "userId", target = "user")
    Author toEntity(AuthorDTO authorDTO); 
    default Author fromId(Long id) {
        if (id == null) {
            return null;
        }
        Author author = new Author();
        author.setId(id);
        return author;
    }
}
