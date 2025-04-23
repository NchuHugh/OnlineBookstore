package com.hugh.dao;

import com.hugh.domain.UserProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.One;
import org.springframework.stereotype.Repository;

/**
 * 用户详细信息Dao接口
 * 使用MyBatis注解方式定义SQL
 */
@Repository
public interface UserProfileDao {
    
    /**
     * 根据用户ID查询用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息对象，如果不存在则返回null
     */
    @Select("SELECT * FROM t_user_profile WHERE user_id = #{userId}")
    UserProfile findByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据详情ID查询用户详细信息
     * @param profileId 详情ID
     * @return 用户详细信息对象，如果不存在则返回null
     */
    @Select("SELECT * FROM t_user_profile WHERE profile_id = #{profileId}")
    UserProfile findById(@Param("profileId") Integer profileId);
    
    /**
     * 查询用户详细信息（包含用户基本信息）
     * @param userId 用户ID
     * @return 用户详细信息对象（包含用户基本信息），如果不存在则返回null
     */
    @Select("SELECT * FROM t_user_profile WHERE user_id = #{userId}")
    @Results({
        @Result(property = "profileId", column = "profile_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "realName", column = "real_name"),
        @Result(property = "address", column = "address"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.hugh.dao.UserDao.findById"))
    })
    UserProfile findProfileWithUser(@Param("userId") Integer userId);
    
    /**
     * 插入用户详细信息
     * @param userProfile 用户详细信息对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO t_user_profile(user_id, real_name, address, phone, updated_at) " +
            "VALUES(#{userId}, #{realName}, #{address}, #{phone}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "profileId")
    int insert(UserProfile userProfile);
    
    /**
     * 更新用户详细信息
     * @param userProfile 用户详细信息对象
     * @return 影响的行数
     */
    @Update("UPDATE t_user_profile SET real_name = #{realName}, address = #{address}, " +
            "phone = #{phone}, updated_at = #{updatedAt} WHERE user_id = #{userId}")
    int update(UserProfile userProfile);
    
    /**
     * 删除用户详细信息
     * @param userId 用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM t_user_profile WHERE user_id = #{userId}")
    int delete(@Param("userId") Integer userId);
}
