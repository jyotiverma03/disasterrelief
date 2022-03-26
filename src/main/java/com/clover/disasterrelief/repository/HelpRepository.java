package com.clover.disasterrelief.repository;

import com.clover.disasterrelief.domain.Help;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Help entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HelpRepository extends MongoRepository<Help, String> {}
