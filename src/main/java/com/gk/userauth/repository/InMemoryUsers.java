package com.gk.userauth.repository;

import com.gk.userauth.domain.User;
import com.gk.userauth.service.UserAuthenticationService;
import com.gk.userauth.service.UserCrudService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

import static java.util.Optional.ofNullable;

@Service
final class InMemoryUsers implements UserCrudService {

    Map<String, User> users = new HashMap<>();

    @NonNull
    UserAuthenticationService authentication;


    @Override
    public User save(final User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public Optional<User> find(final String id) {
        return ofNullable(users.get(id));
    }

    @Override
    public List<User> loggedInUsers() {
        List<User> result = new ArrayList<>();
        for(User user : users.values()){
            result.add(user);
        }
        return result;
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return users
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }

    @Override
    public boolean logout(String token) {
      try {
          users.remove(authentication.findByToken(token));
      } catch (Exception e){
          //logout failed
          return false;
      }

      return true;
    }
}