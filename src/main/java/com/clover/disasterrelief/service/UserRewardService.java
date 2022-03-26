package com.clover.disasterrelief.service;

import com.clover.disasterrelief.domain.UserReward;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UserReward}.
 */
public interface UserRewardService {
    /**
     * Save a userReward.
     *
     * @param userReward the entity to save.
     * @return the persisted entity.
     */
    UserReward save(UserReward userReward);

    /**
     * Partially updates a userReward.
     *
     * @param userReward the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserReward> partialUpdate(UserReward userReward);

    /**
     * Get all the userRewards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserReward> findAll(Pageable pageable);

    /**
     * Get the "id" userReward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserReward> findOne(String id);

    /**
     * Delete the "id" userReward.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
