package com.example.profileem.service;

import com.example.profileem.domain.Party;

import java.util.List;

public interface PartyService {

    Party createParty(String partyName, Long partyLeaderId);

    void inviteUserToParty(Long partyId, Long userId);

    void addCardToParty(Long partyId, Long cardId);
    void replacePartyCard(Long partyId, Long oldCardId, Long newCardId);

}
