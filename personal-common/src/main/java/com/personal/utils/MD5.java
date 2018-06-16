package com.personal.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by daila on 2017/12/13.
 */
public class MD5 {
    public static String MD5Encode(String origin, String charsetName) {
        origin = origin.trim();
        String reusltString = null;

        try {
            reusltString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            reusltString = byteArrayToHexString(md.digest(reusltString.getBytes(charsetName)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return reusltString;
    }

    /**
     * 将byte[] 转换成字符串
     */
    public static String byteArrayToHexString(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }
}
