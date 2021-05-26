package client;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCipher {
    public byte[] hashPassword(String password) {
        byte[] hashedPassword = null;
        try {
            MessageDigest hasher = MessageDigest.getInstance("MD5");
            if (password != null) {
                hashedPassword = hasher.digest(password.getBytes(StandardCharsets.UTF_8));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No MD5 was found");
        }
        return hashedPassword;
    }
}
