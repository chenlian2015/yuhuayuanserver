package com.yuhuayuan.enums;

import java.util.HashMap;
import java.util.Map;

public enum CommunityEnum {
    SUBMITTED(1, "已提交"),
    OPENED(2, "已开通"),
    SHUT_DOWN(3, "已关停"),
    ALL(0, "全部");
    private static final Map<Integer, CommunityEnum> interToEnum = new HashMap<Integer, CommunityEnum>();

    static {
        for (CommunityEnum type : CommunityEnum.values()) {
            interToEnum.put(type.getCode(), type);
        }
    }

    private int code;
    private String msg;

    private CommunityEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommunityEnum fromInteger(int code) {
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
