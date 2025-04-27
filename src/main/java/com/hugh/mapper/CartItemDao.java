package com.hugh.mapper;

import com.hugh.domain.CartItem;
import org.apache.ibatis.annotations.Param;
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
    List<CartItem> findByUserId(Integer userId);

    /**
     * 查询特定用户的特定图书的购物车项
     */
    CartItem findByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    /**
     * 添加购物车项
     */
    int insert(CartItem cartItem);

    /**
     * 更新购物车项数量
     */
    int updateQuantity(@Param("cartItemId") Integer cartItemId, @Param("quantity") Integer quantity);

    /**
     * 删除购物车项
     */
    int delete(Integer cartItemId);

    /**
     * 清空用户的购物车
     */
    int deleteByUserId(Integer userId);
}
