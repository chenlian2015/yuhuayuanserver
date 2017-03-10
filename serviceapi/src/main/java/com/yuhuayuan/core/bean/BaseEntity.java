package com.yuhuayuan.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cl on 2017/3/10.
 */

public abstract class BaseEntity implements Serializable {
    protected long id;
    protected Date createTime;
    protected Date updateTime;

    public BaseEntity() {
    }

    public long getId() {
        return this.id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getId() != other.getId()) {
                return false;
            } else {
                Date this$createTime = this.getCreateTime();
                Date other$createTime = other.getCreateTime();
                if(this$createTime == null) {
                    if(other$createTime != null) {
                        return false;
                    }
                } else if(!this$createTime.equals(other$createTime)) {
                    return false;
                }

                Date this$updateTime = this.getUpdateTime();
                Date other$updateTime = other.getUpdateTime();
                if(this$updateTime == null) {
                    if(other$updateTime != null) {
                        return false;
                    }
                } else if(!this$updateTime.equals(other$updateTime)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseEntity;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        long $id = this.getId();
        int result1 = result * 59 + (int)($id >>> 32 ^ $id);
        Date $createTime = this.getCreateTime();
        result1 = result1 * 59 + ($createTime == null?43:$createTime.hashCode());
        Date $updateTime = this.getUpdateTime();
        result1 = result1 * 59 + ($updateTime == null?43:$updateTime.hashCode());
        return result1;
    }

    public String toString() {
        return "BaseEntity(id=" + this.getId() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
