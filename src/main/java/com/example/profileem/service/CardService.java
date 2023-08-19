package com.example.profileem.service;

import com.example.profileem.domain.Card;

import java.awt.image.BufferedImage;

public interface CardService {
    Card createCard(Card card);
    BufferedImage getCardImageById(Long id);
}
