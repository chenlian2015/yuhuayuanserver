package com.yuhuayuan.core.component.encrypt;

import com.yuhuayuan.api.helper.UserConsts;
import com.yuhuayuan.tool.encrypt.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by cl on 2017/3/8.
 */

public final class TokenSupporter {
    private static final Logger log = LoggerFactory.getLogger(TokenSupporter.class);

    public static String generateToken(long uid) {
        return AESUtil.encrypt(uid + "&" + UUID.randomUUID().toString(), UserConsts.getUserConfig().passportEncryptKey());
    }

    public static long decryptUid(String token) {
        String decryptStr;
        try {
            decryptStr = AESUtil.decrypt(token, UserConsts.getUserConfig().passportEncryptKey());
        } catch (Exception var3) {
            log.warn("decryptUid error", var3);
            return 0L;
        }

        return StringUtils.isBlank(decryptStr)?0L: NumberUtils.toLong(decryptStr.substring(0, decryptStr.indexOf("&")));
    }

    private TokenSupporter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
