package com.example.profileem.repository;

import com.example.profileem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    Optional<User> findByEmail(String email);
}
