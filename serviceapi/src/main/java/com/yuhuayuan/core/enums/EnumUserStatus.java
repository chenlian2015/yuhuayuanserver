package com.yuhuayuan.core.enums;

/**
 * Created by cl on 2017/3/10.
 */
public enum EnumUserStatus {
    ACTIVE(1, "正常"),
    LOCKED(2, "锁定"),
    DELETE(3, "删除");

    private int code;
    private String desc;

    private EnumUserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
