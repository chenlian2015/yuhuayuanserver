package com.yuhuayuan.core.bean.user;

import com.yuhuayuan.database.Pageable;

/**
 * Created by cl on 2017/3/10.
 */
public class SearchUserParam extends Pageable {
    private String mobile;
    private String nickname;
    private long communityId;
    private String startTime;
    private String endTime;

    public SearchUserParam() {
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getNickname() {
        return this.nickname;
    }

    public long getCommunityId() {
        return this.communityId;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof SearchUserParam)) {
            return false;
        } else {
            SearchUserParam other = (SearchUserParam)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    String this$mobile = this.getMobile();
                    String other$mobile = other.getMobile();
                    if(this$mobile == null) {
                        if(other$mobile == null) {
                            break label63;
                        }
                    } else if(this$mobile.equals(other$mobile)) {
                        break label63;
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

                if(this.getCommunityId() != other.getCommunityId()) {
                    return false;
                } else {
                    String this$startTime = this.getStartTime();
                    String other$startTime = other.getStartTime();
                    if(this$startTime == null) {
                        if(other$startTime != null) {
                            return false;
                        }
                    } else if(!this$startTime.equals(other$startTime)) {
                        return false;
                    }

                    String this$endTime = this.getEndTime();
                    String other$endTime = other.getEndTime();
                    if(this$endTime == null) {
                        if(other$endTime != null) {
                            return false;
                        }
                    } else if(!this$endTime.equals(other$endTime)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof SearchUserParam;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        String $mobile = this.getMobile();
        int result1 = result * 59 + ($mobile == null?43:$mobile.hashCode());
        String $nickname = this.getNickname();
        result1 = result1 * 59 + ($nickname == null?43:$nickname.hashCode());
        long $communityId = this.getCommunityId();
        result1 = result1 * 59 + (int)($communityId >>> 32 ^ $communityId);
        String $startTime = this.getStartTime();
        result1 = result1 * 59 + ($startTime == null?43:$startTime.hashCode());
        String $endTime = this.getEndTime();
        result1 = result1 * 59 + ($endTime == null?43:$endTime.hashCode());
        return result1;
    }

    public String toString() {
        return "SearchUserParam(mobile=" + this.getMobile() + ", nickname=" + this.getNickname() + ", communityId=" + this.getCommunityId() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ")";
    }
}

