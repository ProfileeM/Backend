package com.example.profileem.service;

import com.example.profileem.domain.Wallet;

public interface WalletService {
    Wallet addCardToWallet(Long userId, Long cardId);
}
