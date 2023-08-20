package com.example.profileem.service;

import com.example.profileem.domain.User;
import com.example.profileem.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j // 로그 확인
@Service
public class KakaoService {

    final public UserRepository userRepository;

    // 카카오 로그인
    public Long createKakaoUser(String accessToken) {

        String postURL = "https://kapi.kakao.com/v2/user/me"; // 이 주소에 access_token 보내면 사용자 정보 받아올 수 있음

        Long id = null; // 사용자 id

        try {
            URL url = new URL(postURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            log.info("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();

            id = element.getAsJsonObject().get("id").getAsLong();
            String name = properties.getAsJsonObject().get("nickname").getAsString();

            log.info("saveName : " + name); // 저장하는 회원 이름 출력
            log.info("saveId : " + id); // 저장하는 회원 id 출력

            if (userRepository.findByUserId(id) == null) {
                User newUser = User.builder()
                        .userId(id)
                        .name(name)
                        .build();

                userRepository.save(newUser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
