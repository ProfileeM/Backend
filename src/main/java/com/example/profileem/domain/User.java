package com.example.profileem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user") // 테이블 이름 오류
public class User {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

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


//    // 관리자 인지 일반 유저인지
//    @Column(name="role")
//    private String role;

    @Builder
    public User(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
