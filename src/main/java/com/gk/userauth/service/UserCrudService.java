package com.gk.userauth.service;

import com.gk.userauth.domain.User;
import java.util.Optional;

public interface UserCrudService {

    Optional<User> registerUser(User user);

    Optional<User> findByUsername(String username);

}
