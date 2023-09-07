package com.example.profileem.domain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter //setter 메소드 생성
@Getter //getter 메소드 생성
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id") // id
    private Long partyId;

    @Column(name = "party_name") // 모임 이름
    private String partyName;

    @CreationTimestamp // 자동으로 생성 날짜 및 시간 설정
    @Column(name = "creation_date") // 모임 생성 날짜
    private Date creationDate;

    @Column(name = "card_count", columnDefinition = "int default 0")
    private int cardCount; // 파티의 카드 수, 기본값 0

    @Column(name = "party_leader_id") // 방장
    private Long partyLeaderId;

    @ManyToMany
    @JoinTable(
            name = "party_cards",
            joinColumns = @JoinColumn(name = "party_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;

    // 파티에 속한 유저들 (유저들 권한 관리 여기에 있는 유저만 이 모임의 카드를 볼 수 있음)
    @ManyToMany
    @JoinTable(
            name = "party_members",
            joinColumns = @JoinColumn(name = "party_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;

}
