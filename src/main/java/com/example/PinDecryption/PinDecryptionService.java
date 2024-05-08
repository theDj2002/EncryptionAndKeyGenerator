package com.example.PinDecryption;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class PinDecryptionService {

    public String decryptPin(String encrptData) {

        String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"
                + "MIIEpAIBAAKCAQEAwmGCKv916Y3Es2q73tVMAGL3fYSAinOIHGIB3LxATS2p2KFX\r\n"
                + "SMJcby1I/9BCal2+7v7j6/4Rr958dyuGhvUhTPdZAmanJcDDlMnrxD2WOgKhnwzu\r\n"
                + "YZ8tv2lXs9NSeu20YwLrVZoxQZyfSzkGtH1CxyfKKyFtVrmp8qNis2kn2XF4FEss\r\n"
                + "6RNrqc10qzEhQqJidHPTiSdBrHW7VaF/MAOBmw/r4hVQOtKcgHFFJyNQUNM6fOFs\r\n"
                + "JAmZwvPilE7DJhhRASjMH3rMNyKPcqx3h0IirPBKXbMEk1RV4ciwwinYrz+BV/TR\r\n"
                + "EKDerJoFgtvc125wlOun8Ey14C9TzAq1nbhT2wIDAQABAoIBABI+YqA0f9We17Jb\r\n"
                + "NyzP6iUzJLVkJUhZVZIMZM5Z4NMdkQ5i/jU3AMPyhSJ1YnZnPA9kXvH8U56lDBuH\r\n"
                + "O0YZZbm6kWEXT2LMt86UMQunGB1zt8WcyhbhgMKxWWjoUZp2CvMsB/ht02esEotV\r\n"
                + "29o+03CrvF7V8VQtctTyHsZl/TZ8NN5Wd0f6NQpF40ev1eHHmIb8mxfKazbivig3\r\n"
                + "ErUzj67lNRBwWD7JV8ae88JS3roE04jwHwLTUmtspgV8oXFBhMIb8/2wnT9ac2iy\r\n"
                + "Nb7hvVqWK0CDtpQrfUnlqi/FDMH9gIFw/RERSv0c16vV1sRwngIGEaMf5mgk8vI4\r\n"
                + "cay7U/ECgYEA8YuprnOv9g7ux/o1BYBRaoXPK8BcI9U/j1yP8brVQB9RfOSU45p9\r\n"
                + "qIk4L4CN6BcS7gBkgqhvIazCcISS/sPqB9INzXIr3ktddGm4c9spoSBscM5z6Z+p\r\n"
                + "+XQ6ep27Pw97wgA8v7ZRCzRAzjirdq7cs5oyVkvtI2xTbCrwk6+KRwMCgYEAzgNP\r\n"
                + "enHUnxRRWq1xPjG2DIJtdwgd2vzLqpSXEgDtSv7EDchLlL3mDk/sx9pU79Ni7HsB\r\n"
                + "A39AhQp0JITn+D7uUQYfXFXMqeqd/k/DbSGxB+Hat0dJzCvj5sEWWDXcU/5QESB4\r\n"
                + "PoyIAnukuOfSUIE+cH+HGT1tM3nBDn6gBqmXXEkCgYEA3oJd6UrBCdZvuOCQ/2RZ\r\n"
                + "m9twIUmul4PWbWZDY9dMoyk2k+quDY0/vIXLsFaoYuj+kyjhbktjNI5FAAngr7gi\r\n"
                + "WtnxxRQrvTeH+7l4JaH7E2ce/jvxI4HlvtqHFHVBDLJiuzAkxiZZOg0EtfQWX9pt\r\n"
                + "jTW6sSx4Y21i2/4iaKoIo6kCgYBU14wt0x9LfGScwX03reVZdYV7Vf5lp2KBF7y3\r\n"
                + "j2YqUo2PV4O4+BP8pPHQxJviQN4moemi9Q8vO0TlnU6jL9eZyrmznyoweA0lJNhI\r\n"
                + "420y39LhVBySVyj+aVq4bUYV2VBEHWkn6VxCGYWXGPHGcdOzJPIQdPm6D1Rwkbl5\r\n"
                + "xPIu8QKBgQCphmvt8l4ZHnLET7dx22afSOHArtwpvmWt7Eqe80N1OIpBtQ5bzSd6\r\n"
                + "oD1zgsbrVGpRrKHPsGhemw9UcPdnRneLtFAYi60RF2d5POBnTVLtW9phDc20EFGZ\r\n"
                + "vG+Amh3nssE1UQfncYWk77+HXgDOfZ/ozMZm3sNOVvdTL4DUonstFg==\r\n"
                + "-----END RSA PRIVATE KEY-----\r\n"
                + "";

        String privateKeyText = privateKey.replaceAll("\\r\\n", "").replaceAll("-----BEGIN RSA PRIVATE KEY-----", "").replaceAll("-----END RSA PRIVATE KEY-----", "").trim();

        Security.addProvider(new BouncyCastleProvider());
        byte[] privateKeyBytes = org.bouncycastle.util.encoders.Base64.decode(privateKeyText);

        KeyFactory keyFactory = null;
        PrivateKey privateKeys = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKeys = keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


//		String encrptData="MZ4jk6wdKLzPRdDsYYKkpeXFjfr1k3Q9GuKLkIHsCym/5h1DlQjYuV+OQ3rzPKZvgvXYs3LD7eFYl7KrtvRB4PTWU8+3mEU8C7+X3ViN51/6i5xu6hXSuf1sQEAM9hKltdZL4L5NNtMbX26uyhwHyLe55HduiBYK2a3NiIGqc/7cMazqA/+BaCEYhEwQVZqm6Hy54DpSfgR4wHFbnGlwyZ+W3ItUvsAaI9nsh7KS7NSaCW+x7kfJNUWj5w4Tr7rY/ljpy0VsnbvEXUYOdbPZ+2W9lHEkcjVCTh4fA3PzHQMwDjzin4t9Y/LDEztbhZ1ZKEjxlfmu/b79wz3V4NWY1A==";
        byte[] encrptDataa = Base64.getDecoder().decode(encrptData);
        Cipher cipher;
        byte[] decryptedData = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKeys);
            decryptedData = cipher.doFinal(encrptDataa);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace();
        }
        String decryptedString = new String(decryptedData);
        System.out.println("Decrypted PIN data: " + decryptedString);

        return decryptedString;
    }

    public PublicKey getPublicKey(String publicKeyText) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = java.util.Base64.getDecoder().decode(publicKeyText);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    public String encryptedPin(String plainPin) {
        String publicKey = "-----BEGIN PUBLIC KEY-----\r\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmGCKv916Y3Es2q73tVM\r\nAGL3fYSAinOIHGIB3LxATS2p2KFXSMJcby1I/9BCal2+7v7j6/4Rr958dyuGhvUh\r\nTPdZAmanJcDDlMnrxD2WOgKhnwzuYZ8tv2lXs9NSeu20YwLrVZoxQZyfSzkGtH1C\r\nxyfKKyFtVrmp8qNis2kn2XF4FEss6RNrqc10qzEhQqJidHPTiSdBrHW7VaF/MAOB\r\nmw/r4hVQOtKcgHFFJyNQUNM6fOFsJAmZwvPilE7DJhhRASjMH3rMNyKPcqx3h0Ii\r\nrPBKXbMEk1RV4ciwwinYrz+BV/TREKDerJoFgtvc125wlOun8Ey14C9TzAq1nbhT\r\n2wIDAQAB\r\n-----END PUBLIC KEY-----\r\n";
        String publicKeyText = publicKey.replaceAll("\\r\\n", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", "").trim();

        try {
            PublicKey publicKeys = this.getPublicKey(publicKeyText);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, publicKeys);
            byte[] encryptedData = cipher.doFinal(plainPin.getBytes());
            String encryptedString = java.util.Base64.getEncoder().encodeToString(encryptedData);
            System.out.println("Encrypted PIN data: " + encryptedString);
            return encryptedString;
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeySpecException | NoSuchAlgorithmException var8) {
            var8.printStackTrace();
            return null;
        }
    }
}

