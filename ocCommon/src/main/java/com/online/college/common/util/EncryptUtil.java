package com.online.college.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加解密工具类
 *
 * @author yangxin
 * @createtime 2019/04/19 22:20
 */
public class EncryptUtil {
    /**
     * MD5加密
     */
    public static String encodedByMD5(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] results = md.digest(source.getBytes());
            String result = bytesToHex(results);
            if (result != null) {
                return result.toUpperCase();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return null;
    }

    /**
     * 将字节数组转换成十六进制字符的字符串
     */
    private static String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0)
            return null;
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    public static String encryptSHA1(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用AES的CBC模式加密
     *
     * @param key  加密的秘钥
     * @param text 待加密的内容
     * @return 经过Base64编码的密文
     */
    public static String encryptByAESWithCBC(byte[] key, byte[] text) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(key, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(text);
            return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key  经Base64编码的AES秘钥
     * @param text 经Base64编码的加密串
     */
    public static byte[] decryptByAESWithCBC(byte[] key, byte[] text) {
        byte[] original = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(key, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            original = cipher.doFinal(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return original;
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.encodedByMD5("111111"));
    }
}