package com.yuhuayuan.core.dto.user;

import com.yuhuayuan.enums.EnumImageSpec;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@Data
public class User {
    private Long id;

    private Long uid;

    private String mobile;

    private String nickname;

    private String avatar;

    private Date birthday;

    private Byte gender;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    /**
     * 获取头像地址
     *
     * @return
     */
    public String getAvatarUrl(final EnumImageSpec enumImageSpec) {
        if (StringUtils.isBlank(avatar)) {
            return "http://ejiaziimg.goodaa.com.cn/dfc44dd8-b76d-11e6-80f5-76304dec7eb7.png" + enumImageSpec.getName();
        }

        if (avatar.startsWith("default:")) {
            return avatar.substring(8) + enumImageSpec.getName();
        }

        if (avatar.contains("ejiaziimg")) {
            return avatar + enumImageSpec.getName();
        }

        return avatar;
    }

    /**
     * 判断是否是默认生成的昵称<br>
     * 默认昵称为ejz_ 加6位随机数
     *
     * @return
     */
    public boolean withDefaultNickname() {
        return StringUtils.isBlank(nickname)
                || (nickname.startsWith("ejz_") && StringUtils.isNumeric(nickname.substring(4)))
                || (StringUtils.length(mobile) == StringUtils.length(nickname) && this.mobile.startsWith(nickname.substring(0, 3)) && this.mobile.endsWith(nickname.substring(7)));
    }

    /**
     * 是否是默认头像
     *
     * @return
     */
    public boolean withDefaultAvatar() {
        return StringUtils.isBlank(this.avatar) || this.avatar.startsWith("default:");
    }

    /**
     * 获取编码后的手机号，中间几位会被隐去
     *
     * @return
     */
    public String getEncodeMobile() {
        if (StringUtils.isBlank(mobile)) {
            return mobile;
        }

        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }

    public String getNickname() {
        if (this.mobile.equals(this.nickname)) {
            return getEncodeMobile();
        }
        return this.nickname;
    }
}