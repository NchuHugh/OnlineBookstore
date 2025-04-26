package com.hugh.dao;

import com.hugh.domain.Book;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 图书DAO接口
 */
@Repository
public interface BookDao {

    @Select("SELECT book_id, category_id, title, author, publisher, price, stock, cover_image, description FROM t_book")
    @Results({
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "author", column = "author"),
        @Result(property = "publisher", column = "publisher"),
        @Result(property = "price", column = "price"),
        @Result(property = "stock", column = "stock"),
        @Result(property = "coverImage", column = "cover_image"),
        @Result(property = "description", column = "description")
    })
    List<Book> findAll();

    @Select("SELECT book_id, category_id, title, author, publisher, price, stock, cover_image, description FROM t_book WHERE title LIKE CONCAT('%', #{keyword}, '%') OR author LIKE CONCAT('%', #{keyword}, '%')")
    @Results({
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "author", column = "author"),
        @Result(property = "publisher", column = "publisher"),
        @Result(property = "price", column = "price"),
        @Result(property = "stock", column = "stock"),
        @Result(property = "coverImage", column = "cover_image"),
        @Result(property = "description", column = "description")
    })
    List<Book> searchByKeyword(@Param("keyword") String keyword);

    @Select("SELECT book_id, category_id, title, author, publisher, price, stock, cover_image, description FROM t_book WHERE book_id = #{bookId}")
    @Results({
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "author", column = "author"),
        @Result(property = "publisher", column = "publisher"),
        @Result(property = "price", column = "price"),
        @Result(property = "stock", column = "stock"),
        @Result(property = "coverImage", column = "cover_image"),
        @Result(property = "description", column = "description")
    })
    Book findById(@Param("bookId") Integer bookId);
}
