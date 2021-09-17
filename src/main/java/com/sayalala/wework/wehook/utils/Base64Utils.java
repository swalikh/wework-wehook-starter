package com.sayalala.wework.wehook.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Base64Utils {

    public static Key DEFAULT_KEY = null;

    public static final String DEFAULT_SECRET_KEY1 = "?:P)(OL><KI*&UJMNHY^%TGBVFR$#EDCXSW@!QAZ";
    public static final String DEFAULT_SECRET_KEY2 = "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik,9ol.0p;/";
    public static final String DEFAULT_SECRET_KEY3 = "!QAZ@WSX#EDC$RFV%TGB^YHN&UJM*IK<(OL>)P:?";
    public static final String DEFAULT_SECRET_KEY4 = "1qaz@WSX3edc$RFV5tgb^YHN7ujm*IK<9ol.)P:?";
    public static final String DEFAULT_SECRET_KEY5 = "!QAZ2wsx#EDC4rfv%TGB6yhn&UJM8ik,(OL>0p;/";
    public static final String DEFAULT_SECRET_KEY6 = "1qaz2wsx3edc4rfv5tgb^YHN&UJM*IK<(OL>)P:?";
    public static final String DEFAULT_SECRET_KEY = DEFAULT_SECRET_KEY1;

    public static final String DES = "DES";

    public static final Base32 base32 = new Base32();

    static {
        DEFAULT_KEY = obtainKey(DEFAULT_SECRET_KEY);
    }

    /**
     * 获得key
     **/
    public static Key obtainKey(String key) {
        if (key == null) {
            return DEFAULT_KEY;
        }
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
            //替换开始
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(secureRandom);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode(String str) {
        return encode64(null, str);
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode64(String key, String str) {
        return Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode32(String key, String str) {
        return base32.encodeAsString(obtainEncode(key, str.getBytes())).replaceAll("=", "");
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode16(String key, String str) {
        return Hex.encodeHexString(obtainEncode(key, str.getBytes()));
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode(String str) {
        return decode64(null, str);
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode64(String key, String str) {
        return new String(obtainDecode(key, Base64.decodeBase64(str)));
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode32(String key, String str) {
        return new String(obtainDecode(key, base32.decode(str)));
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode16(String key, String str) {
        try {
            return new String(obtainDecode(key, Hex.decodeHex(str.toCharArray())));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密<br>
     * 以byte[]明文输入,byte[]密文输出
     */
    private static byte[] obtainEncode(String key, byte[] str) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密<br>
     * 以byte[]密文输入,以byte[]明文输出
     */
    private static byte[] obtainDecode(String key, byte[] str) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    public static void main(String[] args) {
        System.out.println(Base64Utils.encode("超级管理员"));
        System.out.println(Base64Utils.decode("5KyZLCeOwHQ"));
    }
}
