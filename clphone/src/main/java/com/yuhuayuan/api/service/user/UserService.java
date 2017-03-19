package com.yuhuayuan.api.service.user;

import com.yuhuayuan.core.bean.user.SearchUserParam;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.core.dto.user.UserWeixinInfo;
import com.yuhuayuan.database.Pageable;
import com.yuhuayuan.enums.EnumGender;
import com.yuhuayuan.tool.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Created by cl on 2017/3/10.
 */

public interface UserService {

    /**
     * 创建用户
     *
     * @param mobile
     * @return
     */
    User createUser(String mobile);

    /**
     * 根据uid获取用户
     *
     * @param uid
     * @return
     */
    Optional<User> getByUid(long uid);

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    Optional<User> getByMobile(String mobile);

    /**
     * 根据uid列表批量获取用户
     *
     * @param uids
     * @return 根据用户id列表排序
     */
    List<User> batchGetByUids(List<Long> uids);

    /**
     * 根据用户手机列表批量获取用户
     *
     * @param mobiles
     * @return 根据用户id列表排序
     */
    List<User> batchGetByMobiles(List<String> mobiles);

    /**
     * 根据昵称获取
     *
     * @param nickname
     * @return
     */
    Optional<User> getByNickname(String nickname);

    /**
     * 设置头像
     *
     * @param uid
     * @param avatar
     */
    void setAvatar(long uid, String avatar);


    /**
     * 设置昵称
     *
     * @param uid
     * @param nickname
     */
    void setNickname(long uid, String nickname);

    /**
     * 检查昵称是否已存在
     *
     * @param nickname
     * @return
     */
    boolean isNicknameExist(String nickname);

    /**
     * 设置生日
     *
     * @param uid
     * @param birthday
     */
    void setBirthday(long uid, Date birthday);

    /**
     * 设置性别
     *
     * @param uid
     * @param gender
     */
    void setGender(long uid, EnumGender gender);

    /**
     * 通过微信信息更新用户的个人信息
     * @param userWeixinInfo
     */
    void updateByWeixinInfo(UserWeixinInfo userWeixinInfo);

    /**
     * 查询用户全列表
     *
     *
     * @param pageable @return
     */
    Page<User> getPage(Pageable pageable);

    /**
     * 根据条件查询用户
     * FIXME 需要跨三张表查询
     *
     * @param searchUserParam
     * @return
     */
    Page<User> search(SearchUserParam searchUserParam);

}
