package com.yuhuayuan.api.service.user;

import com.google.common.collect.Maps;
import com.yuhuayuan.core.bean.user.SearchUserParam;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.core.dto.user.UserWeixinInfo;
import com.yuhuayuan.core.enums.EnumUserStatus;
import com.yuhuayuan.core.persistence.UserMapper;
import com.yuhuayuan.enums.EnumGender;
import com.yuhuayuan.tool.Page;
import com.yuhuayuan.tool.Pageable;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by cl on 2017/3/10.
 */

@Service
public class UserServiceImpl implements UserService {

    private final String[] avatars = {"http://ejiaziimg.goodaa.com.cn/dfc44720-b76d-11e6-80f5-76304dec7eb7.png",
            "http://ejiaziimg.goodaa.com.cn/dfc44c02-b76d-11e6-80f5-76304dec7eb7.png",
            "http://ejiaziimg.goodaa.com.cn/dfc44d06-b76d-11e6-80f5-76304dec7eb7.png",
            "http://ejiaziimg.goodaa.com.cn/dfc44dd8-b76d-11e6-80f5-76304dec7eb7.png"};
    @Autowired
    private UserMapper userDao;
    //@Autowired
    //private BaseUserService baseUserService;

    @Transactional
    public User createUser(final String mobile) {
        final User user = new User();
        user.setMobile(mobile);
        user.setNickname(getRandomNickName());
        user.setAvatar(getRandomAvatar());
        user.setStatus((byte)EnumUserStatus.ACTIVE.getCode());
        userDao.save(user);

        if (user.getUid() <= 0) {
            throw new RuntimeException("save user error, because uid = 0");
        }

        final long uid = user.getUid();

// cld       baseUserService.initPushToken(uid);
// cld       baseUserService.initLoginRecord(uid);
// cld       baseUserService.initUserSelectedCommunity(uid);
// cld       baseUserService.initUserThirdRelation(uid);

        return user;
    }

    public Optional<User> getByUid(final long uid) {
        if (uid <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(userDao.selectByUid(uid));
    }

    @Override
    public Optional<User> getByMobile(final String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return Optional.empty();
        }

        return Optional.ofNullable(userDao.selectByMobile(mobile));
    }

    @Override
    public List<User> batchGetByUids(final List<Long> uids) {
        if (CollectionUtils.isEmpty(uids)) {
            return Collections.emptyList();
        }

        final List<User> userList = userDao.batchSelectByUids(uids);
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }

        final Map<Long, User> userMap = Maps.uniqueIndex(userList, User::getUid);

        final List<User> result = new ArrayList<>(userMap.size());
        for (final Long uid : uids) {
            result.add(userMap.get(uid));
        }
        return result;
    }

    public List<User> batchGetByMobiles(final List<String> mobiles) {
        if (CollectionUtils.isEmpty(mobiles)) {
            return Collections.emptyList();
        }

        final List<User> users = userDao.batchSelectByMobiles(mobiles);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        final Map<String, User> userMap = Maps.uniqueIndex(users, User::getMobile);
        final List<User> result = new ArrayList<>(users.size());
        for (final String mobile : mobiles) {
            result.add(userMap.get(mobile));
        }

        return result;
    }

    @Override
    public Optional<User> getByNickname(final String nickname) {
        if (StringUtils.isBlank(nickname)) {
            return Optional.empty();
        }

        return Optional.ofNullable(userDao.selectByNickname(nickname));
    }

    public void setAvatar(final long uid, final String avatar) {
        userDao.updateAvatar(uid, avatar);
    }

    public void setNickname(final long uid, final String nickname) {
        userDao.updateNickname(uid, nickname);
    }

    public boolean isNicknameExist(final String nickname) {
        return getByNickname(nickname).isPresent();
    }

    public void setBirthday(final long uid, final Date birthday) {
        userDao.updateBirthday(uid, birthday);
    }

    @Override
    public void setGender(final long uid, final EnumGender gender) {
        userDao.updateGender(uid, gender.getCode());
    }

    @Override
    public void updateByWeixinInfo(final UserWeixinInfo userWeixinInfo) {
        final Optional<User> userOptional = getByUid(userWeixinInfo.getUid());
        if (!userOptional.isPresent()) {
            return;
        }

        final User user = userOptional.get();
        if (user.withDefaultAvatar() && StringUtils.isNotBlank(userWeixinInfo.getHeadimgUrl())) {
            user.setAvatar(userWeixinInfo.getHeadimgUrl());
        }
        if (user.withDefaultNickname()) {
            String nickname = userWeixinInfo.getNickname();
            while (isNicknameExist(nickname)) {
                nickname = userWeixinInfo.getNickname() + '_' + RandomStringUtils.randomNumeric(8);
            }
            user.setNickname(nickname);
        }
        if (user.getGender() == EnumGender.UNKNOWN.getCode()) {
            user.setGender((byte)userWeixinInfo.getSex());
        }

        userDao.update(user);
    }

    @Override
    public Page<User> getPage(final Pageable pageable) {
        final long total = userDao.countAll();
        final List<User> users = userDao.selectPage(pageable);
        return Page.<User>pageBuilder(pageable).total(total).content(users).build();
    }

    @Override
    public Page<User> search(final SearchUserParam searchUserParam) {
        if (searchUserParam.getCommunityId() == 0) {
            final List<User> users = userDao.selectByParamIgnoreCommunityId(searchUserParam);
            final long total = userDao.countByParamIgnoreCommunityId(searchUserParam);
            return Page.<User>pageBuilder(searchUserParam).total(total).content(users).build();
        } else {
            final List<User> users = userDao.selectByParam(searchUserParam);
            final long total = userDao.countByParam(searchUserParam);
            return Page.<User>pageBuilder(searchUserParam).total(total).content(users).build();
        }
    }

    private String getRandomNickName() {
        String randomNickname = "ejz_" + RandomStringUtils.randomNumeric(8);
        while (isNicknameExist(randomNickname)) {
            randomNickname = "ejz_" + RandomStringUtils.randomNumeric(8);
        }
        return randomNickname;
    }

    private String getRandomAvatar() {
        return "default:" + avatars[RandomUtils.nextInt(0, 4)];
    }
}

