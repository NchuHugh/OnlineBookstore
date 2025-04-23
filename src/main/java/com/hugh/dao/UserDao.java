package com.hugh.dao;

import com.hugh.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Repository;

/**
 * 用户Dao接口
 * 使用MyBatis注解方式定义SQL
 */
@Repository
public interface UserDao {
    
    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return 用户对象，如果不存在则返回null
     */
    @Select("SELECT user_id, username, password, email, role, created_at FROM t_user WHERE user_id = #{userId}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "role", column = "role"),
        @Result(property = "createdAt", column = "created_at")
    })
    User findById(@Param("userId") Integer userId);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    @Select("SELECT user_id, username, password, email, role, created_at FROM t_user WHERE username = #{username}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "role", column = "role"),
        @Result(property = "createdAt", column = "created_at")
    })
    User findByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户对象，如果不存在则返回null
     */
    @Select("SELECT user_id, username, password, email, role, created_at FROM t_user WHERE email = #{email}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "role", column = "role"),
        @Result(property = "createdAt", column = "created_at")
    })
    User findByEmail(@Param("email") String email);
    
    /**
     * 插入新用户
     * @param user 用户对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO t_user(username, password, email, role, created_at) " +
            "VALUES(#{username}, #{password}, #{email}, #{role}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 影响的行数
     */
    @Update("UPDATE t_user SET username = #{username}, password = #{password}, " +
            "email = #{email}, role = #{role} WHERE user_id = #{userId}")
    int update(User user);
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM t_user WHERE user_id = #{userId}")
    int delete(@Param("userId") Integer userId);
    
    /**
     * 验证用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，如果验证失败则返回null
     */
    @Select("SELECT user_id, username, password, email, role, created_at FROM t_user WHERE username = #{username} AND password = #{password}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "role", column = "role"),
        @Result(property = "createdAt", column = "created_at")
    })
    User verifyLogin(@Param("username") String username, @Param("password") String password);
}
