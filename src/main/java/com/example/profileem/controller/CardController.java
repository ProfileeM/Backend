package com.example.profileem.controller;

import com.example.profileem.domain.Card;
import com.example.profileem.repository.CardRepository;
import com.example.profileem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.*;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    @Autowired
    private CardService cardService; // Card 관련 서비스 클래스를 주입받음


    @PostMapping("/") //my 카드 등록 // qr생성 후 저장하는 로직 추가
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = cardService.createCard(card);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    //특정 카드 조회
    @GetMapping("/{id}") // my 카드 중 특정 카드 조회
    public ResponseEntity<Card> getCardById(@PathVariable Long id) { //id : cardId
        Optional<Card> card = cardRepository.findById(id);
        return card.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //특정 카드 삭제
    @DeleteMapping("/{id}") // 특정 카드 삭제
    public ResponseEntity<String> deleteCardById(@PathVariable Long id) { //id : cardId
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            cardRepository.delete(card.get());
            return new ResponseEntity<>("Card has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Card not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all") //모든 카드 조회
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @DeleteMapping("/all") //모든 카드 삭제
    public ResponseEntity<String> deleteAllCards() {
        cardRepository.deleteAll();
        return new ResponseEntity<>("All cards have been deleted.", HttpStatus.OK);
    }

    @GetMapping("/{id}/qrimage") // my 카드 중 특정 카드 qr코드 조회
    public ResponseEntity<byte[]> getQRImageById(@PathVariable Long id) {
        BufferedImage qrImage = cardService.getQRImageById(id);

        if (qrImage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return ResponseEntity
                    .ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}