package com.example.profileem.repository;

import com.example.profileem.domain.Party;
import com.example.profileem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    @Query("SELECT p FROM Party p JOIN p.members m WHERE m.userId = :userId")
    List<Party> findPartiesWhereUserIsMember(@Param("userId") Long userId);

}
