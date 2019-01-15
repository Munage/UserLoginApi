package com.gk.userauth.controller;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class LoginResponse {
    Long id;
    String token;

    public LoginResponse(Long userId, String token) {
        this.id = userId;
        this.token = token;
    }


}
