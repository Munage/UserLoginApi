package com.gk.userauth.service;

import com.gk.userauth.domain.User;
import com.gk.userauth.dto.LogoutResponse;

import java.util.Optional;

public interface UserAuthenticationService {
    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<User> findByToken(String token);

    /**
     * Logs out the session with the given {@code token}
     *
     * @param token
     * @return an {@link LogoutResponse} of the logout operation
     */
    LogoutResponse logout(String token);
}
