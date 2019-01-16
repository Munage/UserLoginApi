package com.gk.userauth.service;

public interface UserSessionService {
    /**
     * Counts the number of active user sessions
     * @return the total number of valid user sessions
     */
    int countActiveSessions();
}
