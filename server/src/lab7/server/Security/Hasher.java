package lab7.server.Security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String sha512(String s) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest
                .getInstance("SHA-512")
                .digest(s.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder hashed = new StringBuilder(bigInt.toString(16));
        while (hashed.length() < 32) {
            hashed.insert(0, '0');
        }
        return hashed.toString();
    }
}
