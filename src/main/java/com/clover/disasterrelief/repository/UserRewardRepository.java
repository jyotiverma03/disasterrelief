package com.clover.disasterrelief.repository;

import com.clover.disasterrelief.domain.UserReward;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserReward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRewardRepository extends MongoRepository<UserReward, String> {}
