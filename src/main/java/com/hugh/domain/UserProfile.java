package com.hugh.domain;

import java.util.Date;

/**
 * 用户详细信息实体类
 * 对应数据库中的t_user_profile表
 */
public class UserProfile {
    /**
     * 详情ID，主键，自增
     */
    private Integer profileId;
    
    /**
     * 用户ID，外键，关联t_user表的user_id
     */
    private Integer userId;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 收货地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 更新时间
     */
    private Date updatedAt;
    
    /**
     * 关联用户对象，便于查询
     */
    private User user;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "profileId=" + profileId +
                ", userId=" + userId +
                ", realName='" + realName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
