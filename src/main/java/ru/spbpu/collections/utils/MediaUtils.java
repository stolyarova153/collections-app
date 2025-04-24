package ru.spbpu.collections.utils;

import java.net.URLEncoder;
import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class MediaUtils {

    public static final String ATTACHMENT_FILENAME_PATTERN = "attachment; filename=\"%s\"";

    public static String encodeUTF8(final String source) {

        if (isBlank(source)) {
            return source;
        }

        return URLEncoder.encode(source, UTF_8).replace("+", " ");
    }

    public static <T> String md5(final T input) {

        try {
            final StringBuilder hash = new StringBuilder();
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            final int firstConstant = 0x0100;
            final int secondConstant = 0x00FF;

            for (final byte aDigest : messageDigest.digest(String.valueOf(input).getBytes())) {
                hash.append(Integer.toHexString(firstConstant + (aDigest & secondConstant)).substring(1));
            }

            return hash.toString();
        } catch (final Throwable t) {
            return null;
        }
    }

}
