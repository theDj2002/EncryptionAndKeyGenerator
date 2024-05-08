package com.example.base64Encoder;

import java.util.Base64;

public class Base64Encoder {

    public static String encode(String plainText) {
        byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes());
        return new String(encodedBytes);
    }

    public static String decode(String base64Text) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Text.getBytes());
        return new String(decodedBytes);
    }

    public static void main(String[] args) {
        String originalText = "00|972345";
        

        String encodedText = encode(originalText);
        System.out.println("Encoded: " + encodedText);

        String decodedText = decode(encodedText);
        System.out.println("Decoded: " + decodedText);
    }
}
