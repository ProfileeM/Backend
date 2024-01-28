package com.example.profileem.repository;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    Optional<Party> findByPartyId(Long partyId);

}
