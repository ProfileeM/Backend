package com.example.profileem.controller;

import com.example.profileem.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class KakaoController {

    public final KakaoService kakaoService;

    @GetMapping("/kakao")
    public Long kakaoLogin(@RequestHeader(value = "AccessToken") String accessToken) {
        Long userId = kakaoService.createKakaoUser(accessToken);

        return userId;
    }
}
