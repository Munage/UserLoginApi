package com.gk.userauth.service.impl;

import com.gk.userauth.models.User;
import com.gk.userauth.repository.UserRepository;
import com.gk.userauth.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserCrudService{

    @Autowired
    UserRepository userRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> registerUser(User user) {
        try {
            //Encrypt the users password before being saved in the DB
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

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

