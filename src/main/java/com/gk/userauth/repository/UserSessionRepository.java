package com.gk.userauth.repository;

import com.gk.userauth.domain.UserSession;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepository extends CrudRepository<UserSession, Long> {

}
