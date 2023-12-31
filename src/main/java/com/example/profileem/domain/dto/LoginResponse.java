package com.example.profileem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
}
