package auth;

import config.ReadProperties;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TokenFactory {
    public static Token generate() {
        String tokenString = generateCookie();

        return new Token(tokenString);
    }

    private static String generateCookie() {
        // Create a random authentication token to be used with the device, in
        // the same way we create a salt for passwords
        SecureRandom randomSalt = new SecureRandom();
        byte[] salt = new byte[Integer.parseInt(ReadProperties
                .getProperty("salt_bytes")) * 2];
        randomSalt.nextBytes(salt);

        BigInteger bigInt = new BigInteger(1, salt);
        String hex = bigInt.toString(16);
        int paddingLength = (salt.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}
