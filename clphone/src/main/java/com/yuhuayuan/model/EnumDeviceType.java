package com.yuhuayuan.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/8.
 */

public enum EnumDeviceType {
    IOS(1, "ios"),
    ANDROID(2, "android");

    private static final Map<String, EnumDeviceType> DESC_ENUM_MAP = new HashMap(values().length);
    private static final Map<Integer, EnumDeviceType> CODE_ENUM_MAP = new HashMap(values().length);
    private int code;
    private String desc;

    EnumDeviceType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnumDeviceType fromDesc(String desc) {
        return (EnumDeviceType)DESC_ENUM_MAP.get(desc);
    }

    public static EnumDeviceType fromCode(int code) {
        return (EnumDeviceType)CODE_ENUM_MAP.get(Integer.valueOf(code));
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    static {
        EnumDeviceType[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            EnumDeviceType enumDeviceType = var0[var2];
            DESC_ENUM_MAP.put(enumDeviceType.getDesc(), enumDeviceType);
            CODE_ENUM_MAP.put(Integer.valueOf(enumDeviceType.getCode()), enumDeviceType);
        }

    }
}
