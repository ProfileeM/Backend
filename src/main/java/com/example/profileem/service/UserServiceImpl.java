package com.example.profileem.service;

import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import com.example.profileem.repository.PartyRepository;
import com.example.profileem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Party> getUserParties(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 사용자가 속한 파티 목록 조회
        List<Party> userParties = partyRepository.findByMembersContains(user);

        return userParties;
    }

}
