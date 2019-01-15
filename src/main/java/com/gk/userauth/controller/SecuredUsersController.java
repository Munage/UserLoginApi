package com.gk.userauth.controller;

import com.gk.userauth.domain.User;
import com.gk.userauth.domain.UserSession;
import com.gk.userauth.dto.UserDto;
import com.gk.userauth.repository.UserRepository;
import com.gk.userauth.repository.UserSessionRepository;
import com.gk.userauth.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {

    @NonNull
    UserCrudService users;

    @Autowired
    UserSessionRepository sessionRepository;




    @RequestMapping(value = "/authenticated", method = RequestMethod.GET, produces = "application/json")
    Map<String, Iterable<UserSession>> getLoggedInUsers(@AuthenticationPrincipal final User user) {
         if(user != null){
             return Collections.singletonMap("users", sessionRepository.findAll());
         }

         return Collections.singletonMap("users", new ArrayList<>());
    }
}