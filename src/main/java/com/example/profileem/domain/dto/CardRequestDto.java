package com.example.profileem.domain.dto;

import com.example.profileem.domain.Card;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardRequestDto {

    private String name;
    private String introduce;
    private String birth;
    private String major;
    private String residence;
    private String mbti;
    private String bad_food;
    private String drink;
    private String interest;
    private String profile;
    private int template;

    public Card toEntity() {
        return Card.builder()
                .nickname(name)
                .introduce(introduce)
                .birth(birth)
                .major(major)
                .residence(residence)
                .mbti(mbti)
                .bad_food(bad_food)
                .drink(drink)
                .interest(interest)
                .profile(profile)
                .template(template)
                .build();
    }
}
