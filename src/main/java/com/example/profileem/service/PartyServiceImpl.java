package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import com.example.profileem.repository.CardRepository;
import com.example.profileem.repository.PartyRepository;
import com.example.profileem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, UserRepository userRepository, CardRepository cardRepository, RedisTemplate<String, Object> redisTemplate) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.redisTemplate = redisTemplate;
    }

    // Redis 캐시 업데이트
    private void updateRedisCache(Party party) {
        redisTemplate.opsForValue().set(getPartyCacheKey(party.getPartyId()), party);
        redisTemplate.expire(getPartyCacheKey(party.getPartyId()), Duration.ofMinutes(party.getCardCount() + 1));
    }

    // Redis 캐시 삭제
    public void deleteFromRedisCache(Long partyId) {
        redisTemplate.delete(getPartyCacheKey(partyId));
    }

    private String getPartyCacheKey(Long partyId) {
        return "Party::" + partyId;
    }

    // 비동기 메소드로 Redis 캐시 업데이트
    @Async
    public void updateRedisCacheAsync(Long partyId) {
        Optional<Party> party = getPartyByPartyId(partyId);
        party.ifPresent(value -> updateRedisCache(value));
    }

    // 비동기 메소드로 Redis 캐시 삭제
    @Async
    public void deleteFromRedisCacheAsync(Long partyId) {
        deleteFromRedisCache(partyId);
    }

    @Override
    @Transactional
    public Party createParty(Party party) {

        Party newParty = Party.builder()
                .partyName(party.getPartyName())
                .partyLeaderId(party.getPartyLeaderId())
                .build();

        partyRepository.save(newParty);
        // 사용자를 추가
        inviteUserToParty(newParty.getPartyId(), party.getPartyLeaderId());

        return partyRepository.save(newParty);
    }

    @Override
    @Transactional
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

        updateRedisCacheAsync(party.getPartyId());
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

        updateRedisCacheAsync(party.getPartyId());
    }

    @Override
    public Optional<Party> getPartyByPartyId(Long party_id) {
        String key = "Party::" + party_id;
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

        // Redis에서 데이터 조회
        Optional<Party> party = Optional.ofNullable((Party) valueOps.get(key));

        if (!party.isPresent()) {
            // Redis에 데이터가 없으면 MySQL에서 조회
            party = partyRepository.findByPartyId(party_id);

            if (party.isPresent()) {
                // MySQL에서 조회한 데이터를 Redis에 저장하고 만료 시간 설정 (예: 1시간)
                valueOps.set(key, party.get());
                redisTemplate.expire(key, Duration.ofMinutes(party.get().getCardCount() + 1));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Party not found");
            }
        }
        return party;
    }


}
