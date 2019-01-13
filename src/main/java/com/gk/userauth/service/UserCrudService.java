package com.gk.userauth.service;

import com.gk.userauth.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserCrudService {
    User save(User user);

    Optional<User> find(String id);

    List<User> loggedInUsers();

    Optional<User> findByUsername(String username);

    boolean logout(String token);

}
