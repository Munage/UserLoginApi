package com.gk.userauth.service;

import com.gk.userauth.domain.User;
import java.util.Optional;

public interface UserCrudService {

    /**
     * Registers a new user
     * @param user
     * @return
     */
    Optional<User> registerUser(User user);

    /**
     * Locates a user based on the username provided
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

}
