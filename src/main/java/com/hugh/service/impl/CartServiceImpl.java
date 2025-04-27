package com.hugh.service.impl;

import com.hugh.mapper.BookDao;
import com.hugh.mapper.CartItemDao;
import com.hugh.domain.Book;
import com.hugh.domain.CartItem;
import com.hugh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemDao cartItemDao;
    
    @Autowired
    private BookDao bookDao;

    @Override
    public List<CartItem> getCartItems(Integer userId) {
        return cartItemDao.findByUserId(userId);
    }

    @Override
    @Transactional
    public boolean addToCart(Integer userId, Integer bookId, Integer quantity) {
        try {
            // 检查图书是否存在
            Book book = bookDao.findById(bookId);
            if (book == null) {
                return false;
            }
            
            // 检查库存是否足够
            if (book.getStock() < quantity) {
                return false;
            }
            
            // 检查购物车中是否已存在该图书
            CartItem existingItem = cartItemDao.findByUserIdAndBookId(userId, bookId);
            
            if (existingItem != null) {
                // 已存在，更新数量
                int newQuantity = existingItem.getQuantity() + quantity;
                if (book.getStock() < newQuantity) {
                    return false; // 库存不足
                }
                return cartItemDao.updateQuantity(existingItem.getCartItemId(), newQuantity) > 0;
            } else {
                // 不存在，创建新购物车项
                CartItem cartItem = new CartItem();
                cartItem.setUserId(userId);
                cartItem.setBookId(bookId);
                cartItem.setQuantity(quantity);
                return cartItemDao.insert(cartItem) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateQuantity(Integer cartItemId, Integer quantity) {
        try {
            return cartItemDao.updateQuantity(cartItemId, quantity) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removeFromCart(Integer cartItemId) {
        try {
            return cartItemDao.delete(cartItemId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean clearCart(Integer userId) {
        try {
            return cartItemDao.deleteByUserId(userId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
