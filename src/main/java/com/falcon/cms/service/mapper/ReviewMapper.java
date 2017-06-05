package com.falcon.cms.service.mapper;

import com.falcon.cms.domain.*;
import com.falcon.cms.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizerMapper.class, PaperMapper.class, })
public interface ReviewMapper extends EntityMapper <ReviewDTO, Review> {

    @Mapping(source = "reviewer.id", target = "reviewerId")

    @Mapping(source = "paper.id", target = "paperId")
    ReviewDTO toDto(Review review); 

    @Mapping(source = "reviewerId", target = "reviewer")

    @Mapping(source = "paperId", target = "paper")
    Review toEntity(ReviewDTO reviewDTO); 
    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
