package com.example.profileem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="name", nullable = false)
    private String name;

    @Builder
    public User(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
