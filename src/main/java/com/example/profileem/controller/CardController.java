package com.example.profileem.controller;

import com.example.profileem.domain.Card;
import com.example.profileem.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping("/") // 내 프로필 카드 등록
    public ResponseEntity<Card> createCard(@RequestBody Card card) {

        // 나머지 카드 정보 설정하고 저장
        Card savedCard = cardRepository.save(card);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}") // 내 프로필 카드 전체 조회
    public ResponseEntity<List<Card>> getCardsByUserId(@PathVariable Long userId) {
        List<Card> cards = cardRepository.findByUserUserId(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{cardId}") // 내 프로필 카드 특정 조회
    public ResponseEntity<Card> getCardByUserIdAndCardId(
            @PathVariable Long userId,
            @PathVariable Long cardId) {
        Optional<Card> card = cardRepository.findByUserUserIdAndCid(userId, cardId);

        return card.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{userId}/{cardId}") // 내 프로필 카드 특정 삭제
    public ResponseEntity<String> deleteCardByUserIdAndCardId(
            @PathVariable Long userId,
            @PathVariable Long cardId) {
        Optional<Card> card = cardRepository.findByUserUserIdAndCid(userId, cardId);
        if (card.isPresent()) {
            cardRepository.delete(card.get());
            return new ResponseEntity<>("Card has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Card not found.", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{userId}/{cardId}") // 내 프로필 카드 수정
    public ResponseEntity<String> updateCardByUserIdAndCardId(
            @PathVariable Long userId, @PathVariable Long cardId,
            @RequestBody Card updatedCard) {
        Optional<Card> optionalCard = cardRepository.findByUserUserIdAndCid(userId, cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            if (updatedCard.getNickname() != null) card.setNickname(updatedCard.getNickname());

            if (updatedCard.getUniversity() != null) card.setUniversity(updatedCard.getUniversity());

            if (updatedCard.getMajor() != null) card.setMajor(updatedCard.getMajor());

            if (updatedCard.getResidence() != null) card.setResidence(updatedCard.getResidence());

            if (updatedCard.getQr() != null) card.setQr(updatedCard.getQr());

            if (updatedCard.getProfile() != null) card.setProfile(updatedCard.getProfile());

            if (updatedCard.getTemplate() != null) card.setTemplate(updatedCard.getTemplate());

            if (updatedCard.getMbti() != null) card.setMbti(updatedCard.getMbti());

            if (updatedCard.getMusic() != null) card.setMusic(updatedCard.getMusic());

            if (updatedCard.getDrink() != null) card.setDrink(updatedCard.getDrink());

            if (updatedCard.getBad_food() != null) card.setBad_food(updatedCard.getBad_food());

            if (updatedCard.getBirth() != null) card.setBirth(updatedCard.getBirth());

            cardRepository.save(card); // 수정한 카드 정보 저장

            return new ResponseEntity<>("Card has been updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Card not found.", HttpStatus.NOT_FOUND);
        }

    }
}
