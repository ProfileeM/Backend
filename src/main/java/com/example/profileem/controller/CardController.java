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

    @PostMapping("/") // 내 프로필 카드 등록 -- 수정해야 될 내용 : 등록할 때 카카오 로그인 userid를 Card의 userid로 넘겨줘야함
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

    @GetMapping("/card/{userId}/{cardId}") // 내 프로필 카드 특정 조회
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
}
