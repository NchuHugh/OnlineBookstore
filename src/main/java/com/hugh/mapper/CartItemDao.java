package com.hugh.mapper;

import com.hugh.domain.CartItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车DAO接口
 */
@Repository
public interface CartItemDao {

    /**
     * 根据用户ID查询购物车项
     */
    @Select("SELECT ci.cart_item_id, ci.user_id, ci.book_id, ci.quantity, " +
            "b.title, b.author, b.price, b.cover_image, b.stock " +
            "FROM t_cart_item ci " +
            "JOIN t_book b ON ci.book_id = b.book_id " +
            "WHERE ci.user_id = #{userId}")
    @Results({
        @Result(property = "cartItemId", column = "cart_item_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "book", javaType = com.hugh.domain.Book.class, column = "book_id", 
            one = @One(select = "com.hugh.dao.BookDao.findById"))
    })
    List<CartItem> findByUserId(Integer userId);

    /**
     * 查询特定用户的特定图书的购物车项
     */
    @Select("SELECT cart_item_id, user_id, book_id, quantity FROM t_cart_item " +
            "WHERE user_id = #{userId} AND book_id = #{bookId}")
    @Results({
        @Result(property = "cartItemId", column = "cart_item_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "quantity", column = "quantity")
    })
    CartItem findByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    /**
     * 添加购物车项
     */
    @Insert("INSERT INTO t_cart_item (user_id, book_id, quantity) VALUES (#{userId}, #{bookId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "cartItemId")
    int insert(CartItem cartItem);

    /**
     * 更新购物车项数量
     */
    @Update("UPDATE t_cart_item SET quantity = #{quantity} WHERE cart_item_id = #{cartItemId}")
    int updateQuantity(@Param("cartItemId") Integer cartItemId, @Param("quantity") Integer quantity);

    /**
     * 删除购物车项
     */
    @Delete("DELETE FROM t_cart_item WHERE cart_item_id = #{cartItemId}")
    int delete(Integer cartItemId);

    /**
     * 清空用户的购物车
     */
    @Delete("DELETE FROM t_cart_item WHERE user_id = #{userId}")
    int deleteByUserId(Integer userId);
}
