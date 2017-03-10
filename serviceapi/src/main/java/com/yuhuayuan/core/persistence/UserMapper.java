package com.yuhuayuan.core.persistence;


import com.yuhuayuan.core.bean.user.SearchUserParam;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.tool.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int save(User var1);

    User selectByUid(long var1);

    User selectByMobile(String var1);

    List<User> batchSelectByUids(@Param("uids") List<Long> var1);

    List<User> batchSelectByMobiles(@Param("mobiles") List<String> var1);

    User selectByNickname(String var1);

    int updateAvatar(@Param("uid") long var1, @Param("avatar") String var3);

    int updateNickname(@Param("uid") long var1, @Param("nickname") String var3);

    int updateBirthday(@Param("uid") long var1, @Param("birthday") Date var3);

    int updateGender(@Param("uid") long var1, @Param("gender") int var3);

    List<User> selectByParamIgnoreCommunityId(SearchUserParam var1);

    long countByParamIgnoreCommunityId(SearchUserParam var1);

    List<User> selectByParam(SearchUserParam var1);

    long countByParam(SearchUserParam var1);

    long countAll();

    List<User> selectPage(Pageable var1);

    int update(User var1);
}