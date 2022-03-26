package com.clover.disasterrelief.repository;

import com.clover.disasterrelief.domain.BaseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the BaseModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseModelRepository extends MongoRepository<BaseModel, String> {}
