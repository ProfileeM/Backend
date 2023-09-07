package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import com.example.profileem.repository.CardRepository;
import com.example.profileem.repository.PartyRepository;
import com.example.profileem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Party createParty(String partyName, Long partyLeaderId) {
        User partyLeader = userRepository.findById(partyLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("Party leader not found"));

        Party party = Party.builder()
                .partyName(partyName)
                .creationDate(new Date())
                .partyLeaderId(partyLeader.getUserId())
                .build();

        // 사용자를 추가
        party.getMembers().add(partyLeader);

        return partyRepository.save(party);
    }

    @Override
    public void inviteUserToParty(Long partyId, Long userId) {
        // 파티 엔티티를 찾기
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        // 초대된 사용자 엔티티를 찾기
        User invitedUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 사용자를 추가
        party.getMembers().add(invitedUser);

        // 저장
        partyRepository.save(party);
    }

    @Override
    public void addCardToParty(Long partyId, Long cardId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        // 파티의 cardIds에 카드 추가
        party.getCards().add(card);

        // 파티의 카드 수 증가
        party.setCardCount(party.getCardCount() + 1);

        // 변경 내용 저장
        partyRepository.save(party);
    }

    @Override
    public void replacePartyCard(Long partyId, Long oldCardId, Long newCardId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        Card oldCard = cardRepository.findById(oldCardId)
                .orElseThrow(() -> new IllegalArgumentException("Old card not found"));

        Card newCard = cardRepository.findById(newCardId)
                .orElseThrow(() -> new IllegalArgumentException("New card not found"));

        // 파티의 카드 목록에서 기존 카드를 제거하고 새 카드를 추가
        if (!party.getCards().remove(oldCard)) {
            throw new IllegalStateException("Old card not found in the party");
        }
        party.getCards().add(newCard);

        partyRepository.save(party);
    }
}
