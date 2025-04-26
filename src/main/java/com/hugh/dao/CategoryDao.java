package com.hugh.dao;

import com.hugh.domain.Category;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 图书分类DAO
 */
@Repository
public interface CategoryDao {
    
    @Select("SELECT category_id, name, description FROM t_category")
    @Results({
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description")
    })
    List<Category> findAll();

    @Select("SELECT category_id, name, description FROM t_category WHERE category_id = #{categoryId}")
    @Results({
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description")
    })
    Category findById(@Param("categoryId") Integer categoryId);
}
