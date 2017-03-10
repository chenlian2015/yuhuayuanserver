package com.yuhuayuan.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/10.
 */
public enum EnumBoolean {
    TRUE(1),
    FALSE(0);

    private static final Map<Integer, EnumBoolean> ENUM_MAP = new HashMap(values().length);
    private final int code;

    private EnumBoolean(int code) {
        this.code = code;
    }

    public static EnumBoolean fromCode(int code) {
        return (EnumBoolean)ENUM_MAP.get(Integer.valueOf(code));
    }

    public int getCode() {
        return this.code;
    }

    static {
        EnumBoolean[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            EnumBoolean enumBoolean = var0[var2];
            ENUM_MAP.put(Integer.valueOf(enumBoolean.getCode()), enumBoolean);
        }

    }
}
