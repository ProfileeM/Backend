package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Wallet;
import com.example.profileem.repository.CardRepository;
import com.example.profileem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    private final CardRepository cardRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, CardRepository cardRepository) {
        this.walletRepository = walletRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Wallet addCardToWallet(Long userId, Long cardId) {
        // 자신의 유저아이디로 된 wallet이 있는지 확인
        Wallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            // 사용자의 wallet이 없으면 새로 생성
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet = walletRepository.save(wallet);
        } else {
            // 이미 해당 cardId가 wallet에 있는지 확인
            if (wallet.getCardIds().contains(cardId)) {
                // 이미 존재하는 경우 예외 처리 또는 메시지 반환
                throw new IllegalStateException("Card already exists in the wallet.");
            }
        }

        // Card 찾기
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        // 사용자의 wallet에 cardId를 저장
        wallet.getCardIds().add(cardId);
        walletRepository.save(wallet);
        return wallet;
    }
}
