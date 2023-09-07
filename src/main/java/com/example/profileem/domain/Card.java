package com.example.profileem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//엔티티 생성후 확인
@Setter //setter 메소드 생성
@Getter //getter 메소드 생성
@Builder
@AllArgsConstructor
@Entity(name="card") //테이블 명 : card
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cid; //card_id => 기본 키로 설정 (AUTO_INCREMENT)

    @Column(name="user_id") //카카오로그인에서 user_id 받아오기
    private Long uid; //user_id => 외래 키로 나중에 설정 수정 (user 테이블)
    
    // 모임이랑 다대다 관계
    @ManyToMany(mappedBy = "cards")
    private List<Party> parties;

    @Column(name="nickname") //unique=true : 이 속성은 해당 컬럼의 값들이 유일해야한다.
    private String nickname; //nickname

    @Column(name="university")
    private String university; //university

    @Column(name="major")
    private String major; //major

    @Column(name="residence")
    private String residence; //residence

    @Column(name="QR_url", length = 1000)   // 길이 수정
    private String qr; //QR_url

    @Column(name="profile")
    private String profile; //profile_url

    @Column(name="template")
    private String template; //template_url

    @Column(name="mbti")
    private String mbti;

    @Column(name="music")
    private String music;

    @Column(name="drink")
    private String drink;

    @Column(name="bad_food")
    private String bad_food;

    @Column(name="birth")
    private String birth;
    public Card(){

    }
    public Card(String nickname, String university, String major, String residence,
                String qr, String template, String profile,
                Long uid,
                String mbti, String drink, String music, String bad_food, String birth) {
        this.nickname = nickname;
        this.university = university;
        this.major = major;
        this.residence = residence;

        this.qr = qr;
        this.profile = profile;
        this.template = template;

        this.uid = uid;

        this.mbti = mbti;
        this.bad_food = bad_food;
        this.music=music;
        this.birth=birth;
        this.drink=drink;

    }
}