package com.example.profileem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//엔티티 생성후 확인
@Setter //setter 메소드 생성
@Getter //getter 메소드 생성 // 기본 생성자
@Builder
@AllArgsConstructor
@Entity(name="card") //테이블 명 : card
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cid; //card_id => 기본 키로 설정 (AUTO_INCREMENT)


    @ManyToOne
    @JoinColumn(name="user_id") //foreign key (user_id) reference User (user_id)
    private User user; //참조할 테이블

    // 모임이랑 다대다 관계
    @ManyToMany(mappedBy = "cards")
    private List<Party> parties;

    @Column(name="nickname")//
    private String nickname; //nickname

    @Column(name="introduce")
    private String introduce; //introduce

    @Column(name="major")
    private String major; //major

    @Column(name="residence")
    private String residence; //residence

    @Column(name="QR_url",length = 1000)
    private String qr; //QR_url

    @Column(name="profile")
    private String profile; //profile_url

    @Column(name="template")
    private int template; //template_url

    @Column(name="mbti")
    private String mbti;

    @Column(name="drink")
    private String drink;

    @Column(name="bad_food")
    private String bad_food;

    @Column(name="birth")
    private String birth;

    @Column(name = "interest")
    private String interest;

    public Card(){

    }
    public Card(String nickname, String introduce, String major, String residence,
                String qr, int template, String profile,
                User user,
                String mbti, String drink, String bad_food, String birth,
                String interest) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.major = major;
        this.residence = residence;

        this.qr = qr;
        this.profile = profile;
        this.template = template;


        this.user = user; //수정

        this.mbti = mbti;
        this.bad_food = bad_food;
        this.birth=birth;
        this.drink=drink;
        this.interest = interest;
    }
}