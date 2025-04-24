package ru.spbpu.collections.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

@Component
public class MD5Encoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence p) {

        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(p.toString().getBytes());
            return printHexBinary(messageDigest.digest()).toLowerCase();
        } catch (final NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    @Override
    public boolean matches(final CharSequence p1, final String p2) {
        return encode(p1).equals(p2);
    }
}
