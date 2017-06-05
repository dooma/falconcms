package com.falcon.cms.service;

import com.falcon.cms.service.dto.ReviewDTO;
import java.util.List;

/**
 * Service Interface for managing Review.
 */
public interface ReviewService {

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    ReviewDTO save(ReviewDTO reviewDTO);

    /**
     *  Get all the reviews.
     *
     *  @return the list of entities
     */
    List<ReviewDTO> findAll();

    /**
     *  Get the "id" review.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReviewDTO findOne(Long id);

    /**
     *  Delete the "id" review.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
