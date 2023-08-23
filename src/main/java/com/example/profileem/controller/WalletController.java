package com.example.profileem.controller;

import com.example.profileem.domain.Wallet;
import com.example.profileem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/{cardId}") // qr코드 받았을 때 qr코드에 card_id가 담겨 있음 요청할 때 자신의 user_id로 api요청
    public ResponseEntity<Wallet> addCardToWallet(
            @RequestParam("user_id") Long userId,
            @PathVariable("cardId") Long cardId) {
        try {
            Wallet wallet = walletService.addCardToWallet(userId, cardId);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
