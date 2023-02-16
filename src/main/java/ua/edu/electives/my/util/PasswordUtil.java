package ua.edu.electives.my.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Class for password encryption

 */
public class PasswordUtil {

    private PasswordUtil() {}

    public static String encrypt(String password) {
        StringBuffer hashedPassword = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            String hexByte;
            for (int i = 0; i < digest.length; i++) {
                hexByte = Integer.toHexString(0xFF & digest[i]);
                if (hexByte.length() == 1) {
                    hexByte = "0" + hexByte;
                }
                hashedPassword.append(hexByte);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword.toString();
    }

}
