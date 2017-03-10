package com.yuhuayuan.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/8.
 */

public enum EnumSource {
    UNKNOW(0),
    APP(1),
    H5(2);

    /**
     * enum map
     */
    private static final Map<Integer, EnumSource> ENUM_MAP = new HashMap<>(EnumSource.values().length);

    static {
        for (EnumSource enumSource : EnumSource.values()) {
            ENUM_MAP.put(enumSource.getCode(), enumSource);
        }
    }

    @Getter
    private int code;

    EnumSource(int code) {
        this.code = code;
    }

    public EnumSource fromCode(final int code) {
        final EnumSource enumSource = ENUM_MAP.get(code);
        return enumSource == null ? UNKNOW : enumSource;
    }
}
