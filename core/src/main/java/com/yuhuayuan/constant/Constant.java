package com.yuhuayuan.constant;

/**
 * Created by cl on 2017/3/8.
 */
public class Constant {
    public static final String REQUEST_USER_BINDER = "request_user_binder";
    public static final String REQUEST_BODY_BINDER = "request_body_binder";
    public static final String REQUEST_HEADER_BINDER = "request_header_binder";

    public static final String PASSPORT_ERROR_MSG_KEY = "PASSPORT_ERROR_MSG";

    public static final int ACK_ERR_EMPTY_TICKET = 200;
    public static final String ACK_ERR_EMPTY_TICKET_MSG = "用户未登录!";

    public static final int ACK_ERR_INVALID_TICKET = 201;
    public static final String ACK_ERR_INVALID_TICKET_MSG = "登录信息失效!";

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

    public static final String COMMON_HEADER_COMMUNITYID = "community_id";

    public static final String ILLEGAL_REQUEST = "207";





    //phone check code
    public static final String CACHE_KEY_SMS_VERIFY_CODE_PREFIX = "sms.verify.code.";
    public static final String CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM = "sms.verify.code.error.num.";

}
