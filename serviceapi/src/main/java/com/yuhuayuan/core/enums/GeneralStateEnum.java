package com.yuhuayuan.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/2/23.
 */

public enum GeneralStateEnum {

    DEFAULT(0, "默认"),
    YES(1, "可用/全部"),
    NO(2, "不可用/非全部"),
    DELETE(5, "删除");

    private static final Map<Integer, GeneralStateEnum> interToEnum = new HashMap<Integer, GeneralStateEnum>();

    static {
        for (GeneralStateEnum type : GeneralStateEnum.values()) {
            interToEnum.put(type.getCode(), type);
        }
    }

    private int code;
    private String msg;

    private GeneralStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static GeneralStateEnum fromInteger(int code) {
        return interToEnum.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

