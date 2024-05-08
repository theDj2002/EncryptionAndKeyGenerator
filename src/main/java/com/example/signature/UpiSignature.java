package com.example.signature;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class UpiSignature {
    public static void main(String[] args) {

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecsp = new ECGenParameterSpec("secp256k1");
            kpg.initialize(ecsp);

            KeyPair kyp = kpg.genKeyPair();
            PublicKey pubKey = kyp.getPublic();

            PrivateKey privateKey = kyp.getPrivate();
            System.out.println("\n\n");

            //Signature with Sha-256
            Signature dsa = Signature.getInstance("SHA256withECDSA");
            dsa.initSign(privateKey);

            String intentURL = " <Mandate name=\"Mandate\" txnId=\"UCZf62514a2ef8646398ebeaeef2564be79\" umn=\"135e6508177241bd81b6f24c97c51ef0@mypsp\" ts=\"2024-02-01T12:37:39+05:30\" revokeable=\"Y\" shareToPayee=\"Y\" type=\"ONETIME\" blockFund=\"Y\">\n" +
                    "        <Validity start=\"01022024\" end=\"01022024\"></Validity>\n" +
                    "        <Amount value=\"10.00\" rule=\"MAX\"></Amount>\n" +
                    "        <Recurrence pattern=\"ONETIME\"></Recurrence>\n" +
                    "    </Mandate>";

            //System.out.println("\nintentURL:" + intentURL);

            byte[] strByte = intentURL.getBytes(StandardCharsets.UTF_8);

            dsa.update(strByte);

            //Sign with private key
            byte[] realSig = dsa.sign();

            //Encode signed URL with base 64

            String encodedSign = java.util.Base64.getEncoder().encodeToString(realSig);

            //Append with Signed URL

            String finalBHIMURL = intentURL + "&sign=" + encodedSign;


            System.out.println("\nSignature: " + new BigInteger(1, realSig).toString(16));

            System.out.println("\nEncoded Signature:" + encodedSign);

            System.out.println("\nFinal BHIM URL:" + finalBHIMURL);


            //Decode the signed URL with base 64

            String[] bhimReceivedURL = finalBHIMURL.split("&sign=");

            byte[] decodedSign =
                    java.util.Base64.getDecoder().decode(bhimReceivedURL[1]);

            System.out.println("\nDecoded Signature: " + new BigInteger(1,
                    decodedSign).toString(16));

            //Initialise verification with public key
            dsa.initVerify(pubKey);

            //Initialise using plain text bytes
            dsa.update(bhimReceivedURL[0].getBytes(StandardCharsets.UTF_8));
            //Verify against sign
            boolean verified = dsa.verify(decodedSign);

            System.out.println("\nSignature Verify Result:" + verified);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
