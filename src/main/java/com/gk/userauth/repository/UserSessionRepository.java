package com.gk.userauth.repository;

import com.gk.userauth.domain.UserSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserSessionRepository extends CrudRepository<UserSession, Long> {
    Optional<UserSession> findByAuthToken(String token);

    Optional<UserSession> findByUsername(String username);
}
