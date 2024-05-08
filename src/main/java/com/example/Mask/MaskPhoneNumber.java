package com.example.Mask;

public class MaskPhoneNumber {

    public static void main(String[] args) {

        String number = "3451234356456787";
        StringBuilder maskValue = new StringBuilder();

            if (number.length() > 0) {
                for (int i = 0; i < number.length(); i++) {
                    maskValue.append("*");
                }
            }

        System.out.println("Masked Value of 1234 is " + maskValue);
    }
}
