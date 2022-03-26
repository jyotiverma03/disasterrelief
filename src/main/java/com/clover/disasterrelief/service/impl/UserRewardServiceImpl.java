package com.clover.disasterrelief.service.impl;

import com.clover.disasterrelief.domain.UserReward;
import com.clover.disasterrelief.repository.UserRewardRepository;
import com.clover.disasterrelief.service.UserRewardService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link UserReward}.
 */
@Service
public class UserRewardServiceImpl implements UserRewardService {

    private final Logger log = LoggerFactory.getLogger(UserRewardServiceImpl.class);

    private final UserRewardRepository userRewardRepository;

    public UserRewardServiceImpl(UserRewardRepository userRewardRepository) {
        this.userRewardRepository = userRewardRepository;
    }

    @Override
    public UserReward save(UserReward userReward) {
        log.debug("Request to save UserReward : {}", userReward);
        return userRewardRepository.save(userReward);
    }

    @Override
    public Optional<UserReward> partialUpdate(UserReward userReward) {
        log.debug("Request to partially update UserReward : {}", userReward);

        return userRewardRepository
            .findById(userReward.getId())
            .map(existingUserReward -> {
                if (userReward.getRating() != null) {
                    existingUserReward.setRating(userReward.getRating());
                }
                if (userReward.getUserId() != null) {
                    existingUserReward.setUserId(userReward.getUserId());
                }
                if (userReward.getBadgeLevel() != null) {
                    existingUserReward.setBadgeLevel(userReward.getBadgeLevel());
                }

                return existingUserReward;
            })
            .map(userRewardRepository::save);
    }

    @Override
    public Page<UserReward> findAll(Pageable pageable) {
        log.debug("Request to get all UserRewards");
        return userRewardRepository.findAll(pageable);
    }

    @Override
    public Optional<UserReward> findOne(String id) {
        log.debug("Request to get UserReward : {}", id);
        return userRewardRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete UserReward : {}", id);
        userRewardRepository.deleteById(id);
    }
}
