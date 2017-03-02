package com.yuhuayuan.common;

public class Constant {

	public static final String REQUEST_PARAMETER_TICKET = "_t";

	public static final String DEFAULT_CHAR_SET = "utf-8";
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	
    public static final int ACK_ERR_EMPTY_TICKET = 200;
    public static final String ACK_ERR_EMPTY_TICKET_MSG = "用户未登录!";

    public static final int ACK_ERR_INVALID_TICKET = 201;
    public static final String ACK_ERR_INVALID_TICKET_MSG = "登录信息错误!";

    public static final int ACK_ERR_EXPIRE_TICKET = 202;
    public static final String ACK_ERR_EXPIRE_TICKET_MSG = "登录信息失效!";

    public static final int ACK_ERROR_SIG_ILLEGAL = 203;
    public static final String ACK_ERROR_SIG_ILLEGAL_MSG = "request sig llegal";

    public static final int ACK_ERROR_COMPANY_NOT_FILL = 204;
    public static final String ACK_ERROR_COMPANY_NOT_FILL_MSG = "user company is not fill";

    public static final int ACK_USER_CANT_NOT_BE_NULL = 205;
    public static final String ACK_USER_CANT_NOT_BE_NULL_MSG = "用户不存在!";

    public static final int ACK_USER_WAS_LOGIN_BY_OTHER_DEVICE = 206;
    public static final String ACK_USER_WAS_LOGIN_BY_OTHER_DEVICE_MSG = "用户登录其他设备!";
}
