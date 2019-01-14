package com.gk.userauth.service.impl;

import com.gk.userauth.domain.User;
import com.gk.userauth.service.TokenService;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
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

    @Override
    public Optional<String> login(final String username, final String password) {
        Optional<String> result = users
                .findByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));

        System.out.println("Login result: " + result.get());

        return result;
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUsername);
    }

    @Override
    public boolean logout(final String token) {
        // Nothing to doy
//        return users.logout(token);
        return true;
    }
}