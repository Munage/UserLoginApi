package com.gk.userauth.controller;

import com.gk.userauth.domain.User;
import com.gk.userauth.dto.LoginResponse;
import com.gk.userauth.dto.LogoutResponse;
import com.gk.userauth.dto.UserDto;
import com.gk.userauth.exceptions.UserAlreadyExistsException;
import com.gk.userauth.repository.UserRepository;
import com.gk.userauth.repository.UserSessionRepository;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import com.gk.userauth.service.impl.UserService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class UsersController {
    @Autowired
    UserAuthenticationService authentication;
    @Autowired
    UserCrudService users;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSessionRepository sessionRepository;

    /**
     * @param username
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = "application/json")
    LoginResponse registerUser(
            @RequestParam("username") final String username,
            @RequestParam("phone") final String phone,
            @RequestParam("password") final String password) {

        userService
                .registerUser(new User(username, phone, password))
                .orElseThrow(() -> new UserAlreadyExistsException("Username already in use"));

        return login(username, password);
    }

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    Map getUsers(@AuthenticationPrincipal final User user) {
        ArrayList<UserDto> result = new ArrayList<>();
        userRepository.findAll().forEach(user1 -> result.add(new UserDto(user1)));

        return Collections.singletonMap("users", result);
    }

    /**
     * @param user
     * @return
     */
    @RequestMapping(value = "/users/authenticated", method = RequestMethod.GET, produces = "application/json")
    Map getLoggedInUsers(@AuthenticationPrincipal final User user) {
        return Collections.singletonMap("active_sessions", userService.countActiveSessions());
    }

    /**
     * @param username
     * @param password
     * @return
     */
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

    /**
     * @param token
     * @return
     */
    @RequestMapping(value = "/logout/{id}", method = RequestMethod.POST, produces = "application/json")
    LogoutResponse logout(@PathVariable("id") long userId, @RequestParam("token") final String token) {
        return authentication.logout(token);
    }

    //Exception handling
    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
