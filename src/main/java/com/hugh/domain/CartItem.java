package com.hugh.domain;

import java.math.BigDecimal;

/**
 * 购物车项实体类
 * 对应数据库中的t_cart_item表
 */
public class CartItem {
    /**
     * 购物车项ID，主键，自增
     */
    private Integer cartItemId;
    
    /**
     * 用户ID，外键，关联t_user表的user_id
     */
    private Integer userId;
    
    /**
     * 图书ID，外键，关联t_book表的book_id
     */
    private Integer bookId;
    
    /**
     * 图书数量
     */
    private Integer quantity;
    
    /**
     * 关联图书对象，便于查询
     */
    private Book book;
    
    /**
     * 计算小计金额
     * @return 购物车项的小计金额（单价 * 数量）
     */
    public BigDecimal getSubtotal() {
        if (book != null && book.getPrice() != null) {
            return book.getPrice().multiply(new BigDecimal(quantity));
        }
        return BigDecimal.ZERO;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
