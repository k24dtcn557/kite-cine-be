package vn.id.hph.kitecine.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QrService {
    /**
     * Generate a PNG byte array containing a QR code for the given text.
     *
     * @param text  text encoded in QR
     * @param width image width
     * @param height image height
     * @return PNG bytes
     */
    public byte[] generatePng(String text, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
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
