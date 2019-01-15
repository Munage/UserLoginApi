package com.gk.userauth.dto;

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
