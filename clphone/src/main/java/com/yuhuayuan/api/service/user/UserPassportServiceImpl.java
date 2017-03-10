package com.yuhuayuan.api.service.user;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.yuhuayuan.api.model.UserPassport;
import com.yuhuayuan.core.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Created by cl on 2017/3/10.
 */
@Slf4j
@Service
public class UserPassportServiceImpl implements UserPassportService {

    //@Autowired
    //private RedisTemplate<String, UserPassport> redisTemplate;

    @Override
    public UserPassport create(final long uid) {
        Preconditions.checkArgument(uid > 0, "illegal uid[%s]", uid);

        final UserPassport userPassport = new UserPassport(uid);

        try {
           // redisTemplate.opsForHash().putAll(UserCacheKey.getPassportKey(uid), BeanUtil.describe(userPassport));
        } catch (Exception e) {
            log.error("parse userPassport to map error", e);
            log.error("userPassport -> {}", userPassport);
            Throwables.propagate(e);
        }
        //redisTemplate.expire(UserCacheKey.getPassportKey(uid), 30, TimeUnit.DAYS);

        return userPassport;
    }

    @Override
    public Optional<UserPassport> getByUid(final long uid) {
        Preconditions.checkArgument(uid > 0, "illegal uid[%s]", uid);

        final Map<String, Object> passportMap = new HashedMap();//redisTemplate.<String, Object>opsForHash().entries(UserCacheKey.getPassportKey(uid));
        if (CollectionUtils.sizeIsEmpty(passportMap)) {
            return Optional.empty();
        }
        final UserPassport userPassport = new UserPassport();
        try {
            BeanUtil.populate(userPassport, passportMap);
        } catch (Exception e) {
            log.error("parse userPassport from map error", e);
            log.error("userPassport map -> {}", passportMap);
            Throwables.propagate(e);
        }
        return Optional.of(userPassport);
    }

    @Override
    public void kickoff(final long uid) {
        //redisTemplate.delete(UserCacheKey.getPassportKey(uid));
    }

    @Override
    public void touch(final long uid) {
      /*  final HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        final long now = System.currentTimeMillis();
        final String key = UserCacheKey.getPassportKey(uid);
        opsForHash.put(key, "lastAccessTime", now);
        opsForHash.put(key, "expireTime", now + UserConsts.getUserConfig().tokenExpireMillis());
        redisTemplate.expire(key, 30, TimeUnit.DAYS);*/
    }

}

