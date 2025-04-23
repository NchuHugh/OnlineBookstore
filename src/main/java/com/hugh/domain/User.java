package com.hugh.domain;

import java.util.Date;

/**
 * 用户实体类
 * 对应数据库中的t_user表
 */
public class User {
    /**
     * 用户ID，主键，自增
     */
    private Integer userId;
    
    /**
     * 用户名，唯一
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 电子邮箱，唯一
     */
    private String email;
    
    /**
     * 用户角色，默认为'customer'，可选值：'customer'(普通用户),'admin'(管理员)
     */
    private String role;
    
    /**
     * 创建时间
     */
    private Date createdAt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
