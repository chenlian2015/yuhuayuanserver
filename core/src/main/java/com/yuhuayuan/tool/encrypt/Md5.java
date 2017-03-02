package com.yuhuayuan.tool.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cl on 2017/3/2.
 */

public class Md5 {
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public Md5() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + bByte);
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        return String.valueOf(iRet);
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for(int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }

        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;

        try {
            new String(strObj);
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }

    public static void main(String[] args) {
        Md5 getMD5 = new Md5();
        System.out.println(GetMD5Code("000000"));
        System.out.println(GetMD5Code("000000"));
    }
}