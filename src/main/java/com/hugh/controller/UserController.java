package com.hugh.controller;

import com.hugh.domain.User;
import com.hugh.domain.UserProfile;
import com.hugh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 处理用户注册、登录和基础CRUD操作的HTTP请求
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * user 用户信息
     * userProfile 用户详细信息（可选）
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> requestMap) {
        try {
            // 从请求体中提取用户信息
            User user = new User();
            user.setUsername((String) requestMap.get("username"));
            user.setPassword((String) requestMap.get("password"));
            user.setEmail((String) requestMap.get("email"));
            
            // 从请求体中提取用户详细信息（如果有）
            UserProfile userProfile = null;
            Map<String, Object> profileMap = (Map<String, Object>) requestMap.get("profile");
            if (profileMap != null) {
                userProfile = new UserProfile();
                userProfile.setRealName((String) profileMap.get("realName"));
                userProfile.setAddress((String) profileMap.get("address"));
                userProfile.setPhone((String) profileMap.get("phone"));
            }
            
            // 调用服务层进行注册
            Integer userId = userService.register(user, userProfile);
            
            if (userId != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("userId", userId);
                return Result.success("注册成功", data);
            } else {
                return Result.error("注册失败，用户名或邮箱已存在");
            }
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登录
     *  username 用户名
     *  password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        User user = userService.login(username, password);
        
        if (user != null) {
            // 登录成功，将用户信息存入会话
            session.setAttribute("user", user);
            return Result.success("登录成功", user);
        } else {
            return Result.error("登录失败，用户名或密码错误");
        }
    }

    /**
     * 用户登出
     * @param session HTTP会话
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        // 清除会话中的用户信息
        session.removeAttribute("user");
        return Result.success("已成功登出");
    }

    /**
     * 获取当前登录用户信息
     * @param session HTTP会话
     * @return 用户信息
     */
    @GetMapping("/current")
    public Result getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("未登录");
        }
    }

    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息
     */
    @GetMapping("/{userId}/profile")
    public Result getUserProfile(@PathVariable Integer userId) {
        UserProfile userProfile = userService.getUserProfile(userId);
        
        if (userProfile != null) {
            return Result.success(userProfile);
        } else {
            return Result.error("未找到用户详细信息");
        }
    }

    /**
     * 更新用户详细信息
     * @param userId 用户ID
     * @param profileInfo 用户详细信息
     * @return 更新结果
     */
    @PutMapping("/{userId}/profile")
    public Result updateUserProfile(@PathVariable Integer userId, 
                                  @RequestBody Map<String, Object> profileInfo) {
        try {
            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(userId);
            userProfile.setRealName((String) profileInfo.get("realName"));
            userProfile.setAddress((String) profileInfo.get("address"));
            userProfile.setPhone((String) profileInfo.get("phone"));
            
            boolean result = userService.updateUserProfile(userProfile);
            
            if (result) {
                return Result.success("用户详细信息更新成功");
            } else {
                return Result.error("用户详细信息更新失败");
            }
        } catch (Exception e) {
            return Result.error("用户详细信息更新失败：" + e.getMessage());
        }
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param passwordInfo 密码信息
     * @return 修改结果
     */
    @PutMapping("/{userId}/password")
    public Result changePassword(@PathVariable Integer userId, 
                               @RequestBody Map<String, String> passwordInfo) {
        String oldPassword = passwordInfo.get("oldPassword");
        String newPassword = passwordInfo.get("newPassword");
        
        boolean result = userService.changePassword(userId, oldPassword, newPassword);
        
        if (result) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("密码修改失败，旧密码可能不正确");
        }
    }
}
