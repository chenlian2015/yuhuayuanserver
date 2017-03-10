package com.yuhuayuan.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/10.
 */

public enum EnumGender {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);

    private static final Map<Integer, EnumGender> ENUM_MAP = new HashMap(values().length);
    private int code;

    private EnumGender(int code) {
        this.code = code;
    }

    public static EnumGender fromCode(int code) {
        EnumGender gender = (EnumGender)ENUM_MAP.get(Integer.valueOf(code));
        return gender == null?UNKNOWN:gender;
    }

    public int getCode() {
        return this.code;
    }

    static {
        EnumGender[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            EnumGender enumAvailable = var0[var2];
            ENUM_MAP.put(Integer.valueOf(enumAvailable.getCode()), enumAvailable);
        }

    }
}

