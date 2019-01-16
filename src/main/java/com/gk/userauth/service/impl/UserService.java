package com.gk.userauth.service.impl;

import com.gk.userauth.domain.User;
import com.gk.userauth.domain.UserSession;
import com.gk.userauth.repository.UserRepository;
import com.gk.userauth.repository.UserSessionRepository;
import com.gk.userauth.service.TokenService;
import com.gk.userauth.service.UserCrudService;
import com.gk.userauth.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService implements UserCrudService, UserSessionService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSessionRepository sessionRepository;
    @Autowired
    TokenService tokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> registerUser(User user) {
        try {
            //Encrypt the users password before being saved in the DB
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            return Optional.of(userRepository.save(user));
        } catch (Exception jdbcException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public int countActiveSessions() {
        Iterable<UserSession> sessions = sessionRepository.findAll();

        for (UserSession session : sessions) {
            System.out.println();
            if (tokenService.verify(session.getAuthToken()).isEmpty()) {
                sessionRepository.delete(session);
            }
        }

        AtomicInteger count = new AtomicInteger();
        sessions.forEach(userSession -> count.getAndIncrement());

        return count.intValue();
    }
}

