package com.gk.userauth.controller;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LogoutResponse {
    String result;

    public LogoutResponse(String result) {
        this.result = result;
    }
}
