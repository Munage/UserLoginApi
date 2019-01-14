package com.gk.userauth.controller;

import com.gk.userauth.domain.User;
import com.gk.userauth.dto.LoginResponse;
import com.gk.userauth.exceptions.UserAlreadyExistAuthenticationException;
import com.gk.userauth.service.impl.UserService;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    UserCrudService repository;

    @NonNull
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.PUT, produces = "application/json")
    Map register(
            @RequestParam("username") final String username,
            @RequestParam("phone") final String phone,
            @RequestParam("password") final String password) {

            userService
                    .registerUser(new User(username, phone, password))
                    .orElseThrow(() -> new RuntimeException("Username already in use"));

            return Collections.singletonMap("token", login(username, password));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    LoginResponse login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        String token = authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));

        Optional<User> user = authentication.findByToken(token);

        return new LoginResponse(user.get().getId(), token);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
    boolean logout(@RequestParam("token") final String token) {
        return authentication.logout(token);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ UserAlreadyExistAuthenticationException.class})
    public void handleException() {
        //
    }
}
