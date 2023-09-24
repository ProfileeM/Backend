package com.example.profileem.controller;

import com.example.profileem.service.KakaoService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
@Tag(name = "Kakao")

public class KakaoController {

    public final KakaoService kakaoService;

    @Operation(summary = "카카오 로그인", description = "카카오 로그인")
    @GetMapping("/kakao")
    public Long kakaoLogin(@RequestHeader(value = "AccessToken") String accessToken) {
        Long userId = kakaoService.createKakaoUser(accessToken);

        return userId;
    }
}
