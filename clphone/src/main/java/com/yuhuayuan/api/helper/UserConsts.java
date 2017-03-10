package com.yuhuayuan.api.helper;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigCache;

import java.util.Map;

/**
 * Created by cl on 2017/3/8.
 */

public final class UserConsts {
    public static UserConsts.UserConfig getUserConfig() {
        return (UserConsts.UserConfig)ConfigCache.getOrCreate(UserConsts.UserConfig.class, new Map[0]);
    }

    private UserConsts() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Sources({"classpath:user-config.properties"})
    public interface UserConfig extends Config {
        @Key("passport.encrypt.key")
        @DefaultValue("bOJgJn2th9ZDPpxg2vHwSfIkTv2ouMpw")
        String passportEncryptKey();

        @Key("token.expire.millis")
        @DefaultValue("2592000000")
        long tokenExpireMillis();

        @Key("login.retry.count")
        @DefaultValue("5")
        int loginRetryCount();
    }
}
