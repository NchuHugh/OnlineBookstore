package com.hugh.service;

import com.hugh.domain.User;
import com.hugh.domain.UserProfile;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 用户注册
     * @param user 用户信息
     * @param userProfile 用户详细信息（可选）
     * @return 注册成功返回用户ID，失败返回null
     */
    Integer register(User user, UserProfile userProfile);
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    User login(String username, String password);
    
    /**
     * 根据ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserById(Integer userId);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新成功返回true，失败返回false
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteUser(Integer userId);
    
    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息对象
     */
    UserProfile getUserProfile(Integer userId);
    
    /**
     * 更新用户详细信息
     * @param userProfile 用户详细信息对象
     * @return 更新成功返回true，失败返回false
     */
    boolean updateUserProfile(UserProfile userProfile);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改成功返回true，失败返回false
     */
    boolean changePassword(Integer userId, String oldPassword, String newPassword);
}
