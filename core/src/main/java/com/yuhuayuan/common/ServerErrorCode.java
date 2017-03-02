package com.yuhuayuan.common;


public enum ServerErrorCode {
    SUCCESS("1", "success"),

    FAILED("0", "参数错误"),

    EC_300001("-300001", "订单不存在!"),

    EC_400000("-400000", "消息体格式错误"),

    EC_400001("-400001", "服务器内部错误"),

    EC_400002("-400002", "请求数据不存在"),

    EC_400003("-400003", "参数错误"),

    EC_400004("0", "请求参数不正确"),

    EC_400007("-400007", "验证码不正确"),

    EC_400005("-400005", "未登录"),

    EC_400006("-400006", "权限错误"),
    EC_400009("-400009", "签名错误"),
    EC_400008("0", "绑定失败：该微信号已被绑定"),
    EC_401001("-401001", "请输入合法手机号"),

    EC_500001("-500001", "用户不存在"),
    EC_500002("-500002", "用户未绑定任何账号"),
    EC_500003("-500003", "用户已绑定微信和手机"),
    EC_500004("-500004", "同步用户设备信息异常"),

    EC_600001("-600001", "该设备已存在！"),

    EC_700001("-700001", "网关原密码错误"),
    EC_700002("-700002", "该网关已绑定"),
    EC_700003("-700003", "绑定网关失败"),
    EC_700004("-700004", "修改网关密码失败"),
    EC_700005("-700005", "电子锁密码错误"),
    EC_700101("-700101", "未认证"),
    EC_700102("-700102", "非ejiazi wifi"),

    EC_800001("-800001", "未获取订单"),
    EC_800002("-800002", "订单状态已变更，请刷新后操作！"),
    EC_900001("-900001", "优惠券信息错误");

    private String result;
    private String message;

    private ServerErrorCode(String code, String message) {
        this.result = code;
        this.message = message;
    }

    public String getCode() {
        return result;
    }

    public void setCode(String code) {
        this.result = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
