package com.example.keyGenerator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GeneratePrivateKey {

    public static void main(String[] args) {
        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println("Private Key: " + privateKey);
            System.out.println("Public Key: " + publicKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
