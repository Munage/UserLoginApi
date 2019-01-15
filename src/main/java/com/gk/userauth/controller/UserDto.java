package com.gk.userauth.controller;

import com.gk.userauth.models.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String username;
    String phone;

    public UserDto(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.phone = user.getPhone();
    }


}
