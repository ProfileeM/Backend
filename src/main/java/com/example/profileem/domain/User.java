package com.example.profileem.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    private Long userId;

    @Column(nullable = false)
    private String name;

    // 유저에 저장
    @ElementCollection
    @CollectionTable(
            name = "user_card_ids",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "card_id")
    private List<Long> receivedCardIds = new ArrayList<>();

    @Builder
    public User(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
