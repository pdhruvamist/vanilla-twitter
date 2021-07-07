package com.dhruva.test.plain.twitter.repository;

import com.dhruva.test.plain.twitter.model.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LoginRequest, String> {

    Optional<LoginRequest> findByUserId(String userId);
}
