package com.gk.userauth.service;

import java.util.Map;

/**
 * Creates and validates credentials.
 */
public interface TokenService {

    String expiring(Map<String, String> attributes);

    /**
     * Checks the validity of the given credentials.
     *
     * @param token
     * @return attributes if verified
     */
    Map<String, String> verify(String token);
}
