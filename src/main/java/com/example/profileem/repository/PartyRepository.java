package com.example.profileem.repository;

import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByMembersContains(User user);
}
