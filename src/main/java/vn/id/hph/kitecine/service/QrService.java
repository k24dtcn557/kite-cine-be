package vn.id.hph.kitecine.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QrService {

    public byte[] generatePng(String text, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // set small margin (border). Default is 4; setting to 1 reduces border size.
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", os);
                return os.toByteArray();
            }
        } catch (WriterException | IOException e) {
            log.error("Failed to generate QR code for text={}", text, e);
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}
