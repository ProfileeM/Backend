package com.example.profileem.service;

import com.example.profileem.domain.Card;
import com.example.profileem.repository.CardRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;

    @Autowired // 생성자 주입
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        // 카드 생성 및 qr 생성 로직

        // 앱 실행하게 하는 건 프론트 조금 나오면 추가
        Long card_id = card.getCid();
        String Url = "http://52.78.201.205:8080/user/" + card_id + "/card";

        String qrCodeBase64 = generateQRCodeBase64(Url);
        card.setQr(qrCodeBase64);

        return cardRepository.save(card);
    }

    // QR 코드 생성 로직 구현 (생성된 QR 코드를 Base64로 변환)
    private String generateQRCodeBase64(String qrCodeText) {
        try {
            // QR 코드 생성 및 인코딩
            BufferedImage qrCodeImage = generateQRCodeImage(qrCodeText, 200, 200);
            return encodeImageToBase64(qrCodeImage, "PNG");
        } catch (Exception e) {
            return null;
        }
    }

    private BufferedImage generateQRCodeImage(String qrCodeText, int width, int height) throws WriterException {
        // QR 코드 생성 로직
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 크기 이후에 바꾸어야할것같기도
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return qrCodeImage;
    }

    // 이미지를 Base64로 인코딩
    private String encodeImageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @Override
    public BufferedImage getQRImageById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        if (card == null || card.getQr() == null) {
            return null; // 카드가 없거나 QR 코드가 없으면 null 반환
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(card.getQr());
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            return null; // 이미지 디코딩 실패 시 null 반환
        }
    }
}
