package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.domain.dto.CardRequestDto;

import java.awt.image.BufferedImage;

public interface CardService {
//    String createCard(Card card);

    String createCard(CardRequestDto cardRequestDto);

    BufferedImage getQRImageById(Long id);
}
