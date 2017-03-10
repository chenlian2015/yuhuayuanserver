package com.yuhuayuan.api.service.user;

import com.yuhuayuan.api.model.UserPassport;

import java.util.Optional;

/**
 * Created by cl on 2017/3/10.
 */
public interface UserPassportService {
    UserPassport create(long var1);

    Optional<UserPassport> getByUid(long var1);

    void kickoff(long var1);

    void touch(long var1);
}