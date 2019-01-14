//package com.gk.userauth.repository;
//
//import com.gk.userauth.domain.User;
//import com.gk.userauth.service.UserAuthenticationService;
//import com.gk.userauth.service.UserCrudService;
//import lombok.NonNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import javax.swing.text.html.Option;
//import java.util.*;
//
//import static java.util.Optional.ofNullable;
//
//@Service
//final class InMemoryUsers implements UserCrudService {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    Map<Long, User> users = new HashMap<>();
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public InMemoryUsers(BCryptPasswordEncoder bCryptPasswordEncoder){
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @NonNull
//    UserAuthenticationService authentication;
//
//    @Override
//    public User save(final String username, final String phone, final String password) {
//
//        User user = new User(username, phone, password);
//        long id = insert(user);
//        System.out.println("User ID: " + id);
//        user.setId(id);
//        return users.put(id, user);
//    }
//
//    public User findById(long id) {
//        return jdbcTemplate.queryForObject("select * from USER where id=?", new Object[] { id },
//                new BeanPropertyRowMapper<>(User.class));
//    }
//
//    public long insert(User user) {
//        return jdbcTemplate.update("insert into USER (username, phone, password, ) " + "values(?,  ?, ?)",
//                new Object[] { user.getUsername(), user.getPhone(), bCryptPasswordEncoder.encode(user.getPassword()) });
//    }
//
//    @Override
//    public Optional<User> find(final String id) {
//        return ofNullable(users.get(id));
//    }
//
//    @Override
//    public List<User> loggedInUsers() {
//        List<User> result = new ArrayList<>();
//        for(User user : users.values()){
//            result.add(user);
//        }
//        return result;
//    }
//
//    @Override
//    public Optional<User> findByUsername(final String username) {
//        return users
//                .values()
//                .stream()
//                .filter(u -> Objects.equals(username, u.getUsername()))
//                .findFirst();
//    }
//
//    @Override
//    public boolean logout(String token) {
//      try {
//          users.remove(authentication.findByToken(token));
//      } catch (Exception e){
//          //logout failed
//          return false;
//      }
//
//      return true;
//    }
//}