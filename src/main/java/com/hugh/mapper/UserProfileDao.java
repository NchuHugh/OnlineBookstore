package com.hugh.mapper;

import com.hugh.domain.UserProfile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户详细信息Dao接口
 */
@Repository
public interface UserProfileDao {
    
    /**
     * 根据用户ID查询用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息对象，如果不存在则返回null
     */
    UserProfile findByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据详情ID查询用户详细信息
     * @param profileId 详情ID
     * @return 用户详细信息对象，如果不存在则返回null
     */
    UserProfile findById(@Param("profileId") Integer profileId);
    
    /**
     * 查询用户详细信息（包含用户基本信息）
     * @param userId 用户ID
     * @return 用户详细信息对象（包含用户基本信息），如果不存在则返回null
     */
    UserProfile findProfileWithUser(@Param("userId") Integer userId);
    
    /**
     * 插入用户详细信息
     * @param userProfile 用户详细信息对象
     * @return 影响的行数
     */
    int insert(UserProfile userProfile);
    
    /**
     * 更新用户详细信息
     * @param userProfile 用户详细信息对象
     * @return 影响的行数
     */
    int update(UserProfile userProfile);
    
    /**
     * 删除用户详细信息
     * @param userId 用户ID
     * @return 影响的行数
     */
    int delete(@Param("userId") Integer userId);
}
