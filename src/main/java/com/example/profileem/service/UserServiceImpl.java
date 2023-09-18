package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import com.example.profileem.repository.PartyRepository;
import com.example.profileem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PartyRepository partyRepository) {
            this.userRepository = userRepository;
            this.partyRepository = partyRepository;
    }

    // 사용자가 받은 카드 ID 추가
    @Override
    public void addReceivedCardId(Long userId, Long cardId) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUserId(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Long> receivedCardIds = user.getReceivedCardIds();

            // 이미 받은 카드 ID인 경우 추가하지 않음 (중복 방지)
            if (!receivedCardIds.contains(cardId)) {
                receivedCardIds.add(cardId);
                userRepository.save(user); // 변경 사항 저장
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // user_id에 맞는 cardid받아오기
    @Override
    public List<Long> getReceivedCardIds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getReceivedCardIds();
    }

    @Override
    public void deleteReceivedCard(Long userId, Long cardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 사용자가 받은 카드 ID 목록에서 cardId를 제거
        List<Long> receivedCardIds = user.getReceivedCardIds();
        if (receivedCardIds.contains(cardId)) {
            receivedCardIds.remove(cardId);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Card not found in user's received cards");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getUserPartyIds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Long> userPartyIds = new ArrayList<>();
        for (Party party : user.getParties()) {
            userPartyIds.add(party.getPartyId());
        }

        return userPartyIds;
    }

    @Override
    @Transactional
    public void deletePartyInUserGroup(Long userId, Long partyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        // 파티 멤버에서 사용자 삭제
        party.getMembers().remove(user);

        // 사용자의 카드를 파티에서 삭제
        List<Long> userCardIds = user.getReceivedCardIds();
        party.getCards().removeIf(card -> userCardIds.contains(card.getCid()));

        // 사용자가 방장인 경우
        if (user.getUserId().equals(party.getPartyLeaderId())) {
            // 새로운 방장 설정 (첫 번째 멤버로 변경)
            if (!party.getMembers().isEmpty()) {
                User newLeader = party.getMembers().iterator().next();
                party.setPartyLeaderId(newLeader.getUserId());
            }
        }

        // 변경 사항 저장
        userRepository.save(user);

        // 모든 멤버가 삭제되었으면 파티 삭제
        if (party.getMembers().isEmpty()) {
            partyRepository.delete(party);
        } else {
            partyRepository.save(party);
        }
    }

}
