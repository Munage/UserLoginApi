package com.gk.userauth.dto;

import com.gk.userauth.domain.User;
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
