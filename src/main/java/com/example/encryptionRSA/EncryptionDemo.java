package com.example.encryptionRSA;


public class EncryptionDemo {

    public static void main(String[] args) {
        EncryptionServiceImpl service = new EncryptionServiceImpl();

        // Encrypting data
        String originalData = "00|972345 ";
        String encryptedData = service.encrypt(originalData);
        System.out.println("Original Data: " + originalData);
        System.out.println("Encrypted Data: " + encryptedData);

        // Decrypting data
        String decryptedData = service.decrypt(encryptedData);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
