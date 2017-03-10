package com.yuhuayuan.tool.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cl on 2017/3/8.
 */

public final class AES {
    private static final String DEFAULT_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(1, keySpec, ivParameterSpec);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(2, keySpec, ivParameterSpec);
        return cipher.doFinal(input);
    }

    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(input, key, iv, "AES/CBC/PKCS5Padding");
    }

    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return decrypt(input, key, iv, "AES/CBC/PKCS5Padding");
    }

    private AES() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

