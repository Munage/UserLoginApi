package com.gk.userauth.dto;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class LoginResponse {
    String id;
    String token;

    public LoginResponse(String userId, String token) {
        this.id = userId;
        this.token = token;
    }


}
