package com.yuhuayuan.core.dto.user;

import com.yuhuayuan.annotations.IdGenerator;
import com.yuhuayuan.enums.EnumImageSpec;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class User {
    private Long id;

    @IdGenerator(offset = 50000)
    private Long uid;

    private String mobile;

    private String nickname;

    private String avatar;

    private Date birthday;

    private Byte gender;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
}