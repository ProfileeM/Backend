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
    public void kakaoLogin(@RequestHeader(value = "AccessToken") String accessToken) {
        kakaoService.createKakaoUser(accessToken);
    }
}
