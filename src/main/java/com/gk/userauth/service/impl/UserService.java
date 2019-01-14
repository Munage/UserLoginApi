package com.gk.userauth.service.impl;

import com.gk.userauth.domain.User;
import com.gk.userauth.repository.UserRepository;
import com.gk.userauth.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserCrudService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> registerUser(User user) {
        try {
            return Optional.of(userRepository.save(user));
        } catch (Exception jdbcException){
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

