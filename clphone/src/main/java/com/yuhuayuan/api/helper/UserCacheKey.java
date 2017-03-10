package com.yuhuayuan.api.helper;

import com.google.common.base.Joiner;
import lombok.experimental.UtilityClass;
/**
 * Created by cl on 2017/3/8.
 */
@UtilityClass
public class UserCacheKey {

    private static final String PREFIX = "ejiazi:user";
    private static final Joiner KEY_JOINER = Joiner.on(':');

    /**
     * passport key
     *
     * @param uid
     * @return ejiazi:user:passport:uid
     */
    public static String getPassportKey(final long uid) {
        return KEY_JOINER.join(PREFIX, "passport", uid);
    }

}
