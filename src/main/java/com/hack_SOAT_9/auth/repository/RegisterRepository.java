package com.hack_SOAT_9.auth.repository;

import com.hack_SOAT_9.auth.entity.RegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegisterRepository extends MongoRepository<RegisterEntity, String> {
    Optional<RegisterEntity> findByEmail(String email);
}
