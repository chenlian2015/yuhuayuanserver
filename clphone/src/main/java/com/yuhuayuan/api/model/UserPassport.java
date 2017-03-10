package com.yuhuayuan.api.model;

import com.yuhuayuan.api.helper.UserConsts;
import com.yuhuayuan.core.component.encrypt.TokenSupporter;
import com.yuhuayuan.enums.EnumAvailable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cl on 2017/3/10.
 */
@Data
@NoArgsConstructor
public class UserPassport {
    /**
     * 用户id
     */
    private long uid;
    /**
     * 会话的加密token
     */
    private String token;
    /**
     * 会话开始时间
     */
    private long startTime;
    /**
     * 最后访问时间
     */
    private long lastAccessTime;
    /**
     * 过期时间
     */
    private long expireTime;
    /**
     * 会话是否有效
     * {@link }
     */
    private int available;

    public UserPassport(long uid) {
        final long now = System.currentTimeMillis();
        this.uid = uid;
        this.token = TokenSupporter.generateToken(uid);
        this.startTime = now;
        this.lastAccessTime = now;
        this.expireTime = now + UserConsts.getUserConfig().tokenExpireMillis();
        this.available = EnumAvailable.YES.getCode();
    }

    /**
     * 判断token是否已过期
     *
     * @return
     */
    public boolean isExpire() {
        return getExpireTime() < System.currentTimeMillis();
    }

    /**
     * token状态是否正常
     *
     * @return
     */
    public boolean isValid() {
        return getAvailable() == EnumAvailable.YES.getCode();
    }

}
