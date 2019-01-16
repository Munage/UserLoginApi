package com.gk.userauth.service.impl;

import com.gk.userauth.domain.User;
import com.gk.userauth.domain.UserSession;
import com.gk.userauth.dto.LogoutResponse;
import com.gk.userauth.repository.UserSessionRepository;
import com.gk.userauth.service.TokenService;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {
    @NonNull
    TokenService tokens;
    @NonNull
    UserCrudService users;

    @Autowired
    UserSessionRepository sessionRepository;

    @Override
    public Optional<String> login(final String username, final String password) {

        Optional<UserSession> existingSession = sessionRepository.findByUsername(username);

        Optional<String> token = users
                .findByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));

        //If the user had a session, delete it from the db after the new one is created
        if (existingSession.isPresent()) {
            sessionRepository.delete(existingSession.get());
        }

        return token;
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public LogoutResponse logout(final String token) {
        // Nothing to do since jwt tokens cant be killed (unless you make them stateful...),
        // so just remove the user session from the DB

        //Find user by token
        Optional<UserSession> session = sessionRepository.findByAuthToken(token);

        if (session.isPresent()) {
            sessionRepository.delete(session.get());
            return new LogoutResponse("Success - Logged out successfully");
        }

        return new LogoutResponse("Failed - Unable to log out user");

    }
}