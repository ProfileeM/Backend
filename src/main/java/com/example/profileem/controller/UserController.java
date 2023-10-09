package com.example.profileem.controller;

import com.example.profileem.domain.dto.LoginResponse;
import com.example.profileem.service.OAuthService;
import com.example.profileem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;

    // 카카오 로그인
    @PostMapping("/kakao")
    public LoginResponse loginKakao(@RequestParam("code") String authorizationCode) {
        return oAuthService.loginKakao(authorizationCode);
    }

    // 사용자가 받은 개인 프로필 카드 ID 추가
    @PostMapping("/card/{card_id}")
    public ResponseEntity<String> addReceivedCardId(
            @RequestParam("user_id") Long userId,
            @PathVariable("card_id") Long cardId) {
        try {
            userService.addReceivedCardId(userId, cardId);
            return ResponseEntity.ok("Card added to user's received cards");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    // 사용자가 받은 개인 프로필 카드 ID 목록 조희
    @GetMapping("/cards")
    public ResponseEntity<List<Long>> getReceivedCardIds(@RequestParam("user_id") Long userId) {
        try {
            List<Long> receivedCardIds = userService.getReceivedCardIds(userId);
            return ResponseEntity.ok(receivedCardIds);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자가 받은 개인 프로필 카드 삭제
    @DeleteMapping("/card/{card_id}")
    public ResponseEntity<String> deleteReceivedCard(
            @RequestParam("user_id") Long userId,
            @PathVariable("card_id") Long cardId) {
        try {
            userService.deleteReceivedCard(userId, cardId);
            return ResponseEntity.ok("Received card deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or card not found");
        }
    }

    // 사용자가 속한 파티 목록 조회
    @GetMapping("/parties")
    public ResponseEntity<List<Long>> getUserPartyIds(@RequestParam("user_id") Long userId) {
        try {
            List<Long> userPartyIds = userService.getUserPartyIds(userId);
            return ResponseEntity.ok(userPartyIds);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 파티에서 탈퇴
    @DeleteMapping("/party/{party_id}") // 내 그룹에서 파티 삭제
    public ResponseEntity<String> deletePartyInUserGroup(
            @RequestParam("user_id") Long userId,
            @PathVariable("party_id") Long partyId ) {
        try {
            userService.deletePartyInUserGroup(userId, partyId);
            return ResponseEntity.ok("Party deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or party not found");
        }
    }
}
