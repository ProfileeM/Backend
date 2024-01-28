package com.example.profileem.controller;

import com.example.profileem.domain.Party;
import com.example.profileem.repository.PartyRepository;
import com.example.profileem.service.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Tag(name = "Party")
@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    // 파티 조회
    @GetMapping("/{party_id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Party> getPartyByPartyId(
            @PathVariable Long party_id
    ) {
        return partyService.getPartyByPartyId(party_id);
    }

    // 내가 방장인 파티 생성
    // 파티 생성
    @PostMapping("/")
    @Operation(summary = "(내가 방장인 파티) 파티 생성", description = "(내가 방장인 파티) 파티 생성")
    public ResponseEntity<Party> createParty(@RequestBody Party party) {

        try {
            Party newParty = partyService.createParty(party);
            return new ResponseEntity<>(newParty, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자를 파티에 초대
    @Operation(summary = "파티에 사용자 초대", description = "파티에 사용자 초대")
    @PostMapping("/invitation/{party_id}")
    public ResponseEntity<String> inviteUserToParty(
            @PathVariable("party_id") Long partyId,
            @RequestParam("user_id") Long userId) {
        try {
            partyService.inviteUserToParty(partyId, userId);
            return ResponseEntity.ok("User invited to the party.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party or user not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // 파티에 카드 등록
    @Operation(summary = "파티에 카드 등록", description = "파티에 카드 등록")
    @PostMapping("/card/{party_id}")
    public ResponseEntity<String> addCardToParty(
            @PathVariable("party_id") Long partyId,
            @RequestParam("card_id") Long cardId) {
        try {
            partyService.addCardToParty(partyId, cardId);
            return ResponseEntity.ok("Card added to the party");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party or card not found");
        }
    }

    // 파티에 등록했던 카드 교체
    @Operation(summary = "파티에 등록했던 카드 교체", description = "파티에 등록했던 카드 교체")
    @PutMapping("/card/{party_id}")
    public ResponseEntity<String> replacePartyCard(
            @PathVariable("party_id") Long partyId,
            @RequestParam("old_card_id") Long oldCardId,
            @RequestParam("new_card_id") Long newCardId) {
        try {
            partyService.replacePartyCard(partyId, oldCardId, newCardId);
            return ResponseEntity.ok("Party card replaced successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party or card not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}
