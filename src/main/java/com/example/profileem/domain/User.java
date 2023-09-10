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
    @Column(name="user_id")
    private Long userId;

    @Column(name="name", nullable = false)
    private String name;

    // 유저에 저장
    @ElementCollection
    @CollectionTable(
            name = "user_card_ids",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "card_id")
    private List<Long> receivedCardIds = new ArrayList<>();

    // 모임이랑 다대다 관계
    @ManyToMany(mappedBy = "members")
    private List<Party> parties;

    @Builder
    public User(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
