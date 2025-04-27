package com.hugh.mapper;

import com.hugh.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 图书DAO接口
 */
@Repository
public interface BookDao {

    /**
     * 查询所有图书
     */
    List<Book> findAll();

    /**
     * 根据关键词搜索图书
     */
    List<Book> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据ID查询图书
     */
    Book findById(@Param("bookId") Integer bookId);
    
    /**
     * 分页查询所有图书
     */
    List<Book> findAllWithPaging(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 分页搜索图书
     */
    List<Book> searchByKeywordWithPaging(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 获取图书总数
     */
    int count();
    
    /**
     * 获取搜索结果总数
     */
    int countByKeyword(@Param("keyword") String keyword);
}
