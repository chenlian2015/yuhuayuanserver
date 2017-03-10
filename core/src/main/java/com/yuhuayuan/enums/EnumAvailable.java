package com.yuhuayuan.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/10.
 */
public enum EnumAvailable {
    YES(EnumBoolean.TRUE.getCode()),
    NO(EnumBoolean.FALSE.getCode());

    private static final Map<Integer, EnumAvailable> ENUM_MAP = new HashMap(values().length);
    private final int code;

    private EnumAvailable(int code) {
        this.code = code;
    }

    public static EnumAvailable fromCode(int code) {
        return (EnumAvailable)ENUM_MAP.get(Integer.valueOf(code));
    }

    public int getCode() {
        return this.code;
    }

    static {
        EnumAvailable[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            EnumAvailable enumAvailable = var0[var2];
            ENUM_MAP.put(Integer.valueOf(enumAvailable.getCode()), enumAvailable);
        }

    }
}