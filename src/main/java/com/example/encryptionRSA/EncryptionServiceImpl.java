package com.example.encryptionRSA;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionServiceImpl {
    private static final String ALGORITHM = "AES";
    private static final String MODE = "GCM";
    private static final String PADDING = "NoPadding";
    private static final String TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;

    private static final String secretKeyStr = "ASDFGHJASHJKLQWEASDFGHJASHJKLQWE"; // 16-byte secret key
    private static final byte[] secretKey = secretKeyStr.getBytes();

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] iv = cipher.getIV();
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Combine IV and encrypted data
            byte[] combined = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String data) {
        try {
            byte[] combined = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, ALGORITHM);

            // Extract IV from combined data
            int ivLength = 12; // GCM recommended IV length
            byte[] iv = new byte[ivLength];
            System.arraycopy(combined, 0, iv, 0, ivLength);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new javax.crypto.spec.GCMParameterSpec(128, iv));

            byte[] decryptedData = cipher.doFinal(combined, ivLength, combined.length - ivLength);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
