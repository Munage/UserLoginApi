package com.gk.userauth.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class User implements UserDetails {
    String id;
    String username;
    String phone;
    String password;

    @JsonCreator
    public User(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("phone") final String phone,
                @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(username);
        this.username = requireNonNull(username);
        this.phone = phone;
        this.password = requireNonNull(password);
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}