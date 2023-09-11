package com.example.profileem.service;

import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import java.util.List;

public interface UserService {
    void addReceivedCardId(Long userId, Long cardId);
    List<Long> getReceivedCardIds(Long userId);

    void deleteReceivedCard(Long userId, Long cardId);

    List<Long> getUserPartyIds(Long userId);

    void deletePartyInUserGroup(Long userId, Long partyId);
}
