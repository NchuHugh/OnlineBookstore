package com.hugh.service;

import com.hugh.domain.CartItem;
import java.util.List;

/**
 * 购物车服务接口
 */
public interface CartService {
    
    /**
     * 获取用户的购物车项列表
     * @param userId 用户ID
     * @return 购物车项列表
     */
    List<CartItem> getCartItems(Integer userId);
    
    /**
     * 添加图书到购物车
     * @param userId 用户ID
     * @param bookId 图书ID
     * @param quantity 数量
     * @return 是否添加成功
     */
    boolean addToCart(Integer userId, Integer bookId, Integer quantity);
    
    /**
     * 更新购物车项数量
     * @param cartItemId 购物车项ID
     * @param quantity 新数量
     * @return 是否更新成功
     */
    boolean updateQuantity(Integer cartItemId, Integer quantity);
    
    /**
     * 从购物车中删除项
     * @param cartItemId 购物车项ID
     * @return 是否删除成功
     */
    boolean removeFromCart(Integer cartItemId);
    
    /**
     * 清空用户的购物车
     * @param userId 用户ID
     * @return 是否清空成功
     */
    boolean clearCart(Integer userId);
}
