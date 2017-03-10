package com.yuhuayuan.core.dto.user;

import com.yuhuayuan.core.bean.BaseEntity;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cl on 2017/3/10.
 */

public class UserWeixinInfo extends BaseEntity {
    private long uid;
    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgUrl;
    private String privilege;
    private String unionid;

    public static UserWeixinInfo createNew() {
        UserWeixinInfo userWeixinInfo = new UserWeixinInfo();
        userWeixinInfo.setUid(0L);
        userWeixinInfo.setOpenid("");
        userWeixinInfo.setNickname("");
        userWeixinInfo.setSex(0);
        userWeixinInfo.setCity("");
        userWeixinInfo.setCountry("");
        userWeixinInfo.setProvince("");
        userWeixinInfo.setHeadimgUrl("");
        userWeixinInfo.setUnionid("");
        userWeixinInfo.setPrivilege("");
        return userWeixinInfo;
    }

    public String getAvatar640() {
        return this.getAvatarBySize(0);
    }

    public String getAvatar46() {
        return this.getAvatarBySize(46);
    }

    public String getAvatar64() {
        return this.getAvatarBySize(64);
    }

    public String getAvatar96() {
        return this.getAvatarBySize(96);
    }

    public String getAvatar132() {
        return this.getAvatarBySize(132);
    }

    public String getRecoveredNickname() {
        return this.getNickname();
    }

    public boolean isBindWx() {
        return this.uid > 0L;
    }

    private String getAvatarBySize(int size) {
        return size == 0?this.getHeadimgUrl():(StringUtils.isBlank(this.getHeadimgUrl())?null:this.getHeadimgUrl().substring(0, this.getHeadimgUrl().length() - 1) + size);
    }

    public UserWeixinInfo() {
    }

    public long getUid() {
        return this.uid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getSex() {
        return this.sex;
    }

    public String getProvince() {
        return this.province;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getHeadimgUrl() {
        return this.headimgUrl;
    }

    public String getPrivilege() {
        return this.privilege;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String toString() {
        return "UserWeixinInfo(uid=" + this.getUid() + ", openid=" + this.getOpenid() + ", nickname=" + this.getNickname() + ", sex=" + this.getSex() + ", province=" + this.getProvince() + ", city=" + this.getCity() + ", country=" + this.getCountry() + ", headimgUrl=" + this.getHeadimgUrl() + ", privilege=" + this.getPrivilege() + ", unionid=" + this.getUnionid() + ")";
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof UserWeixinInfo)) {
            return false;
        } else {
            UserWeixinInfo other = (UserWeixinInfo)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(!super.equals(o)) {
                return false;
            } else if(this.getUid() != other.getUid()) {
                return false;
            } else {
                label116: {
                    String this$openid = this.getOpenid();
                    String other$openid = other.getOpenid();
                    if(this$openid == null) {
                        if(other$openid == null) {
                            break label116;
                        }
                    } else if(this$openid.equals(other$openid)) {
                        break label116;
                    }

                    return false;
                }

                String this$nickname = this.getNickname();
                String other$nickname = other.getNickname();
                if(this$nickname == null) {
                    if(other$nickname != null) {
                        return false;
                    }
                } else if(!this$nickname.equals(other$nickname)) {
                    return false;
                }

                if(this.getSex() != other.getSex()) {
                    return false;
                } else {
                    String this$province = this.getProvince();
                    String other$province = other.getProvince();
                    if(this$province == null) {
                        if(other$province != null) {
                            return false;
                        }
                    } else if(!this$province.equals(other$province)) {
                        return false;
                    }

                    String this$city = this.getCity();
                    String other$city = other.getCity();
                    if(this$city == null) {
                        if(other$city != null) {
                            return false;
                        }
                    } else if(!this$city.equals(other$city)) {
                        return false;
                    }

                    label87: {
                        String this$country = this.getCountry();
                        String other$country = other.getCountry();
                        if(this$country == null) {
                            if(other$country == null) {
                                break label87;
                            }
                        } else if(this$country.equals(other$country)) {
                            break label87;
                        }

                        return false;
                    }

                    String this$headimgUrl = this.getHeadimgUrl();
                    String other$headimgUrl = other.getHeadimgUrl();
                    if(this$headimgUrl == null) {
                        if(other$headimgUrl != null) {
                            return false;
                        }
                    } else if(!this$headimgUrl.equals(other$headimgUrl)) {
                        return false;
                    }

                    label73: {
                        String this$privilege = this.getPrivilege();
                        String other$privilege = other.getPrivilege();
                        if(this$privilege == null) {
                            if(other$privilege == null) {
                                break label73;
                            }
                        } else if(this$privilege.equals(other$privilege)) {
                            break label73;
                        }

                        return false;
                    }

                    String this$unionid = this.getUnionid();
                    String other$unionid = other.getUnionid();
                    if(this$unionid == null) {
                        if(other$unionid != null) {
                            return false;
                        }
                    } else if(!this$unionid.equals(other$unionid)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserWeixinInfo;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + super.hashCode();
        long $uid = this.getUid();
        result1 = result1 * 59 + (int)($uid >>> 32 ^ $uid);
        String $openid = this.getOpenid();
        result1 = result1 * 59 + ($openid == null?43:$openid.hashCode());
        String $nickname = this.getNickname();
        result1 = result1 * 59 + ($nickname == null?43:$nickname.hashCode());
        result1 = result1 * 59 + this.getSex();
        String $province = this.getProvince();
        result1 = result1 * 59 + ($province == null?43:$province.hashCode());
        String $city = this.getCity();
        result1 = result1 * 59 + ($city == null?43:$city.hashCode());
        String $country = this.getCountry();
        result1 = result1 * 59 + ($country == null?43:$country.hashCode());
        String $headimgUrl = this.getHeadimgUrl();
        result1 = result1 * 59 + ($headimgUrl == null?43:$headimgUrl.hashCode());
        String $privilege = this.getPrivilege();
        result1 = result1 * 59 + ($privilege == null?43:$privilege.hashCode());
        String $unionid = this.getUnionid();
        result1 = result1 * 59 + ($unionid == null?43:$unionid.hashCode());
        return result1;
    }
}
