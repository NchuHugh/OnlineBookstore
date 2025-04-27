-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS testcase;

-- 使用数据库
USE testcase;

-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) DEFAULT 'customer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户详细信息表
CREATE TABLE IF NOT EXISTS t_user_profile (
    profile_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    real_name VARCHAR(50),
    address VARCHAR(200),
    phone VARCHAR(20),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES t_user(user_id) ON DELETE CASCADE
);

-- 图书分类表
CREATE TABLE IF NOT EXISTS t_category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- 图书表
CREATE TABLE IF NOT EXISTS t_book (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    cover_image VARCHAR(200),
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES t_category(category_id)
);

-- 购物车表
CREATE TABLE IF NOT EXISTS t_cart_item (
    cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    book_id INT,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES t_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES t_book(book_id) ON DELETE CASCADE,
    UNIQUE KEY (user_id, book_id)
);

-- 订单表
CREATE TABLE IF NOT EXISTS t_order (
    order_id VARCHAR(50) PRIMARY KEY,
    user_id INT,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    shipping_address VARCHAR(200) NOT NULL,
    recipient_name VARCHAR(50) NOT NULL,
    recipient_phone VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user(user_id)
);

-- 订单项表
CREATE TABLE IF NOT EXISTS t_order_item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(50),
    book_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES t_order(order_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES t_book(book_id)
);
