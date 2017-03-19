package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.bean.user.SearchUserParam;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.database.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByUid(Long uid);

    User selectByMobile(String mobile);

    int updateByPrimaryKeySelective(User record);

    List<User> batchSelectByUids(@Param("uids") List<Long> uids);

    int updateByPrimaryKey(User record);

    User selectByNickname(String nickName);

    /**
     * 修改头像
     *
     * @param uid
     * @param avatar
     * @return
     */
    int updateAvatar(@Param("uid") long uid, @Param("avatar") String avatar);

    /**
     * 修改昵称
     *
     * @param uid
     * @param nickname
     * @return
     */
    int updateNickname(@Param("uid") long uid, @Param("nickname") String nickname);

    /**
     * 修改生日
     *
     * @param uid
     * @param birthday
     * @return
     */
    int updateBirthday(@Param("uid") long uid, @Param("birthday") Date birthday);

    /**
     * 修改性别
     *
     * @param uid
     * @param gender
     * @return
     */
    int updateGender(final @Param("uid") long uid, final @Param("gender") int gender);


    /**
     * 查询所有用户数
     *
     * @return
     */
    long countAll();

    /**
     * 分页获取用户
     *
     * @param pageable
     * @return
     */
    List<User> selectPage(Pageable pageable);


    /**
     * 根据搜索参数查找，忽略掉communityId
     *
     * @param searchUserParam
     * @return
     */
    List<User> selectByParamIgnoreCommunityId(SearchUserParam searchUserParam);


    /**
     * 根据搜索参数查询记录数，忽略掉communityId
     *
     * @param searchUserParam
     * @return
     */
    long countByParamIgnoreCommunityId(SearchUserParam searchUserParam);

    /**
     * 根据搜索参数查找
     *
     * @param searchUserParam
     * @return
     */
    List<User> selectByParam(SearchUserParam searchUserParam);

    /**
     * 根据搜索参数查询记录数
     *
     * @param searchUserParam
     * @return
     */
    long countByParam(SearchUserParam searchUserParam);


    /**
     * 根据手机号批量获取
     * @param mobiles
     * @return
     */
    List<User> batchSelectByMobiles(@Param("mobiles") List<String> mobiles);
}