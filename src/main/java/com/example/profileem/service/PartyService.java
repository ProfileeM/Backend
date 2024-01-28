package com.example.profileem.service;

import com.example.profileem.domain.Party;

import java.util.List;
import java.util.Optional;

public interface PartyService {

    Party createParty(Party party);

    void inviteUserToParty(Long partyId, Long userId);

    void addCardToParty(Long partyId, Long cardId);
    void replacePartyCard(Long partyId, Long oldCardId, Long newCardId);

    Optional<Party> getPartyByPartyId(Long party_id);

    void updateRedisCacheAsync(Long partyId);

    void deleteFromRedisCacheAsync(Long partyId);

}
