package com.example.profileem.repository;

import com.example.profileem.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserUserId(Long userId);
    Optional<Card> findByUserUserIdAndCid(Long userId, Long cardId);

}