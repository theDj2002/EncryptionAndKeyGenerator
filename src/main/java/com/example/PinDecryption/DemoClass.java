package com.example.PinDecryption;

public class DemoClass {
    public static void main(String[] args) {
        PinDecryptionService pinDecryptionService = new PinDecryptionService();
        String Data = "1234";
        String encryptedData = pinDecryptionService.encryptedPin(Data);

    }
}
