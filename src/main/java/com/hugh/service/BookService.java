package com.hugh.service;

import com.hugh.domain.Book;
import java.util.List;

/**
 * 图书服务接口
 */
public interface BookService {
    /** 查询所有图书 */
    List<Book> findAll();
    
    /** 根据关键字（书名或作者）搜索图书 */
    List<Book> searchByKeyword(String keyword);
    
    /** 根据ID获取图书详情 */
    Book getById(Integer bookId);
    
    /** 分页查询所有图书 */
    List<Book> findAllWithPaging(int offset, int limit);
    
    /** 分页搜索图书 */
    List<Book> searchByKeywordWithPaging(String keyword, int offset, int limit);
    
    /** 获取图书总数 */
    int count();
    
    /** 获取搜索结果总数 */
    int countByKeyword(String keyword);
}
