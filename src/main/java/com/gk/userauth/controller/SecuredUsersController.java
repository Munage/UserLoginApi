package com.gk.userauth.controller;

import com.gk.userauth.domain.User;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {

    @NonNull
    UserCrudService users;

    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = "application/json")
    User getCurrent(@AuthenticationPrincipal final User user) {
        return user;
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET, produces = "application/json")
    List<User> getLoggedInUsers(@AuthenticationPrincipal final User user) {
         if(user != null){
             return users.loggedInUsers();
         }

         return new ArrayList<>();
    }
}