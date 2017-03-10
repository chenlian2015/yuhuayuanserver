package com.yuhuayuan.tool.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by cl on 2017/3/8.
 */

public final class AESUtil {
    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    public static String encrypt(String input, String key) {
        byte[] inputByte = input.getBytes();
        byte[] keyByte = key.getBytes();
        byte[] ivByte = Arrays.copyOfRange(keyByte, 0, 16);

        try {
            return Base64.encodeBase64String(AES.encrypt(inputByte, keyByte, ivByte));
        } catch (Exception var6) {
            log.error("AES encrypt error", var6);
            return "";
        }
    }

    public static String decrypt(String input, String key) {
        byte[] inputByte = Base64.decodeBase64(input);
        byte[] keyByte = key.getBytes();
        byte[] ivByte = Arrays.copyOfRange(keyByte, 0, 16);

        try {
            return new String(AES.decrypt(inputByte, keyByte, ivByte));
        } catch (Exception var6) {
            log.error("AES decrypt error", var6);
            return "";
        }
    }

    private AESUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
