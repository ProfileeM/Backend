package com.example.profileem.controller;

import com.example.profileem.domain.Party;
import com.example.profileem.repository.UserRepository;
import com.example.profileem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자가 받은 개인 프로필 카드 ID 추가
    @Operation(summary = "사용자가 받은 개인 프로필 카드 ID 추가", description = "사용자가 받은 개인 프로필 카드 ID 추가")
    @PostMapping("/{user_id}/card")
    public ResponseEntity<String> addReceivedCardId(
            @PathVariable("user_id") Long userId,
            @RequestParam("card_id") Long cardId) {
        try {
            userService.addReceivedCardId(userId, cardId);
            return ResponseEntity.ok("Card added to user's received cards");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    // 사용자가 받은 개인 프로필 카드 ID 목록 조희
    @Operation(summary = "사용자가 받은 개인 프로필 카드 ID 목록 조희", description = "사용자가 받은 개인 프로필 카드 ID 목록 조희")
    @GetMapping("/{user_id}/cards")
    public ResponseEntity<List<Long>> getReceivedCardIds(@PathVariable("user_id") Long userId) {
        try {
            List<Long> receivedCardIds = userService.getReceivedCardIds(userId);
            return ResponseEntity.ok(receivedCardIds);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자가 받은 개인 프로필 카드 삭제
    @Operation(summary = "사용자가 받은 개인 프로필 카드 삭제", description = "사용자가 받은 개인 프로필 카드 삭제")
    @DeleteMapping("/{user_id}/{card_id}")
    public ResponseEntity<String> deleteReceivedCard(
            @PathVariable("user_id") Long userId,
            @PathVariable("card_id") Long cardId) {
        try {
            userService.deleteReceivedCard(userId, cardId);
            return ResponseEntity.ok("Received card deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or card not found");
        }
    }

    // 사용자가 속한 파티 목록 조회
    @Operation(summary = "사용자가 속한 파티 목록 조회", description = "사용자가 속한 파티 목록 조회")
    @GetMapping("/{userId}/parties")
    public ResponseEntity<List<Long>> getUserPartyIds(@PathVariable("userId") Long userId) {
        try {
            List<Long> userPartyIds = userService.getUserPartyIds(userId);
            return ResponseEntity.ok(userPartyIds);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
