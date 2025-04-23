package com.hugh.service.impl;

import com.hugh.domain.User;
import com.hugh.domain.UserProfile;
import com.hugh.dao.UserDao;
import com.hugh.dao.UserProfileDao;
import com.hugh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserProfileDao userProfileDao;

    /**
     * 用户注册
     * 注册时自动设置角色为customer，并设置创建时间
     */
    @Override
    @Transactional
    public Integer register(User user, UserProfile userProfile) {
        // 检查用户名是否已存在
        if (userDao.findByUsername(user.getUsername()) != null) {
            return null; // 用户名已存在
        }
        
        // 检查邮箱是否已存在
        if (userDao.findByEmail(user.getEmail()) != null) {
            return null; // 邮箱已存在
        }
        
        // 设置默认角色和创建时间
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("customer");
        }
        user.setCreatedAt(new Date());
        
        // 插入用户记录
        int result = userDao.insert(user);
        if (result <= 0) {
            return null; // 插入失败
        }
        
        // 如果提供了用户详细信息，则插入详细信息
        if (userProfile != null) {
            userProfile.setUserId(user.getUserId());
            userProfile.setUpdatedAt(new Date());
            userProfileDao.insert(userProfile);
        }
        
        return user.getUserId();
    }

    /**
     * 用户登录
     */
    @Override
    public User login(String username, String password) {
        return userDao.verifyLogin(username, password);
    }

    /**
     * 根据ID查询用户
     */
    @Override
    public User getUserById(Integer userId) {
        return userDao.findById(userId);
    }

    /**
     * 根据用户名查询用户
     */
    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 更新用户信息
     */
    @Override
    public boolean updateUser(User user) {
        // 检查用户是否存在
        User existingUser = userDao.findById(user.getUserId());
        if (existingUser == null) {
            return false;
        }
        
        // 检查用户名是否已被其他用户使用
        User userWithSameUsername = userDao.findByUsername(user.getUsername());
        if (userWithSameUsername != null && !userWithSameUsername.getUserId().equals(user.getUserId())) {
            return false; // 用户名已被其他用户使用
        }
        
        // 检查邮箱是否已被其他用户使用
        User userWithSameEmail = userDao.findByEmail(user.getEmail());
        if (userWithSameEmail != null && !userWithSameEmail.getUserId().equals(user.getUserId())) {
            return false; // 邮箱已被其他用户使用
        }
        
        return userDao.update(user) > 0;
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional
    public boolean deleteUser(Integer userId) {
        // 先删除用户详细信息
        userProfileDao.delete(userId);
        // 再删除用户
        return userDao.delete(userId) > 0;
    }

    /**
     * 获取用户详细信息
     */
    @Override
    public UserProfile getUserProfile(Integer userId) {
        return userProfileDao.findProfileWithUser(userId);
    }

    /**
     * 更新用户详细信息
     */
    @Override
    public boolean updateUserProfile(UserProfile userProfile) {
        // 检查用户是否存在
        User existingUser = userDao.findById(userProfile.getUserId());
        if (existingUser == null) {
            return false;
        }
        
        // 设置更新时间
        userProfile.setUpdatedAt(new Date());
        
        // 检查用户详细信息是否已存在
        UserProfile existingProfile = userProfileDao.findByUserId(userProfile.getUserId());
        if (existingProfile == null) {
            // 不存在则插入
            return userProfileDao.insert(userProfile) > 0;
        } else {
            // 存在则更新
            return userProfileDao.update(userProfile) > 0;
        }
    }

    /**
     * 修改密码
     */
    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        // 验证旧密码是否正确
        User user = userDao.findById(userId);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        
        // 更新密码
        user.setPassword(newPassword);
        return userDao.update(user) > 0;
    }
}
