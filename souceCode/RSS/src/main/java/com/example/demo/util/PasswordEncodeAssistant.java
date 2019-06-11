package com.example.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncodeAssistant {
    public static final String SHA = "SHA";
    public static final String SHA256 = "SHA-256";
    public static final String MD5 = "MD5";

    public static String encode(char[] chapters, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        int length = chapters.length;
        byte[] chapterBytes = new byte[length];
        for (int i = 0; i < length; i++) chapterBytes[i] = (byte) chapters[i];
        md.update(chapterBytes);
        byte[] shaBytes = md.digest();
        StringBuilder hex = new StringBuilder();
        for (byte shaByte : shaBytes) {
            int val = ((int) shaByte) & 0xff;
            if (val < 16) hex.append("0");

            hex.append(Integer.toHexString(val));
        }

        return hex.toString();
    }

    /**
     * 默认为SHA算法加密
     *
     * @param chapters 待加密字符数组
     * @return 加密后的字符串
     */
    public static String encode(char[] chapters) {
        return encode(chapters, SHA);
    }
}
