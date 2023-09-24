package com.example.profileem.controller;

import com.example.profileem.domain.Party;
import com.example.profileem.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name="Party")
@RestController
@RequestMapping("/party")
//@Api(tags = {"Party"})
public class PartyController {

    private final PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    // 내가 방장인 파티 생성
    @Operation(summary = "(내가 방장인 파티) 파티 생성", description = "(내가 방장인 파티) 파티 생성")
    @PostMapping("/")
    public ResponseEntity<Party> createParty(
            @RequestParam("party_name") String partyName,
            @RequestParam("party_leader_id") Long partyLeaderId) {
        try {
            Party party = partyService.createParty(partyName, partyLeaderId);
            return ResponseEntity.ok(party);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 사용자를 파티에 초대
    @Operation(summary = "파티에 사용자 초대", description = "파티에 사용자 초대")
    @PostMapping("/{partyId}/invitation")
    public ResponseEntity<String> inviteUserToParty(
            @PathVariable("partyId") Long partyId,
            @RequestParam("userId") Long userId) {
        try {
            partyService.inviteUserToParty(partyId, userId);
            return ResponseEntity.ok("User invited to the party.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party or user not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 파티 탈퇴


    // 파티에 카드 등록
    @Operation(summary = "파티에 카드 등록", description = "파티에 카드 등록")
    @PostMapping("/{partyId}/card")
    public ResponseEntity<String> addCardToParty(
            @PathVariable("partyId") Long partyId,
            @RequestParam("cardId") Long cardId) {
        try {
            partyService.addCardToParty(partyId, cardId);
            return ResponseEntity.ok("Card added to the party");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party or card not found");
        }
    }

    // 파티에 등록했던 카드 교체
    @Operation(summary = "파티에 등록했던 카드 교체", description = "파티에 등록했던 카드 교체")
    @PutMapping("/{partyId}/card")
    public ResponseEntity<String> replacePartyCard(
            @PathVariable("partyId") Long partyId,
            @RequestParam("oldCardId") Long oldCardId,
            @RequestParam("newCardId") Long newCardId) {
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
