package com.yuhuayuan.core.dto.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String checkCode;
}
