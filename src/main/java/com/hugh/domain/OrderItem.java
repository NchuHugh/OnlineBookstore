package com.hugh.domain;

import java.math.BigDecimal;

/**
 * 订单项实体类
 * 对应数据库中的t_order_item表
 */
public class OrderItem {
    /**
     * 订单项ID，主键，自增
     */
    private Integer itemId;
    
    /**
     * 订单ID，外键，关联t_order表的order_id
     */
    private String orderId;
    
    /**
     * 图书ID，外键，关联t_book表的book_id
     */
    private Integer bookId;
    
    /**
     * 购买数量
     */
    private Integer quantity;
    
    /**
     * 购买时单价（冷余存储，防止图书价格变动影响历史订单）
     */
    private BigDecimal price;
    
    /**
     * 关联图书对象，便于查询
     */
    private Book book;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", orderId='" + orderId + '\'' +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
