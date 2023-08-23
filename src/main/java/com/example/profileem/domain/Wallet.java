package com.example.profileem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
@Entity(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "wallet_id", updatable = false)
    private Long walletId;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(name = "wallet_name") // 모임명
    private String walletName;

    @Column(name = "master") // 그룹장
    private String master;

    @Column(name = "sharing_url") // 공유url
    private String sharingUrl;

    @Column(name = "theme") // 테마
    private String theme;

    @Column(name = "created_at") // 개설일
    private String createdAt;

    @ElementCollection
    // wallet_card_ids테이블이 생성, 각 지갑에 연결된 카드 ID들이 저장
    @CollectionTable(name = "wallet_card_ids", joinColumns = @JoinColumn(name = "wallet_id"))
    @Column(name = "card_id") // 받은 카드들
    private List<Long> cardIds = new ArrayList<>();
    
    @Builder // 빌더패턴으로 객체 생성
    public Wallet(String walletName, String master, String sharingUrl, String theme, String createdAt) {
        this.walletName = walletName;
        this.master = master;
        this.sharingUrl = sharingUrl;
        this.theme = theme;
        this.createdAt = createdAt;
    }
}
