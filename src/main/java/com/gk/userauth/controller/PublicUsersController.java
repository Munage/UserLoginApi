package com.gk.userauth.controller;

import com.gk.userauth.domain.User;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserCrudService users;

    @RequestMapping(value = "/register", method = RequestMethod.PUT, produces = "application/json")
    Map register(
            @RequestParam("username") final String username,
            @RequestParam("phone") final String phone,
            @RequestParam("password") final String password) {
        users
                .save(
                        User
                                .builder()
                                .username(username)
                                .phone(phone)
                                .password(password)
                                .build()
                );

        return Collections.singletonMap("token", login(username, password));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
    boolean logout(@RequestParam("token") final String token) {
        return authentication.logout(token);
    }
}
