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

-- 插入一些初始数据

-- 管理员用户
INSERT INTO t_user (username, password, email, role) VALUES
('admin', '123456', 'admin@bookstore.com', 'admin');

-- 普通用户
INSERT INTO t_user (username, password, email) VALUES
('user1', '123456', 'user1@example.com'),
('user2', '123456', 'user2@example.com');

-- 图书分类
INSERT INTO t_category (name, description) VALUES
('计算机科学', '计算机科学与技术相关书籍'),
('文学小说', '各类文学作品与小说'),
('经济管理', '经济学、管理学相关书籍'),
('历史文化', '历史与文化类书籍');

-- 图书
INSERT INTO t_book (category_id, title, author, publisher, price, stock, description) VALUES
(1, 'Java编程思想', 'Bruce Eckel', '机械工业出版社', 108.00, 100, 'Java经典入门书籍，全面介绍Java编程基础与进阶知识'),
(1, 'Python编程：从入门到实践', 'Eric Matthes', '人民邮电出版社', 89.00, 150, '一本全面的Python编程指南，适合初学者入门'),
(2, '红楼梦', '曹雪芹', '人民文学出版社', 59.80, 200, '中国古典四大名著之一，描述了贾、史、王、薛四大家族的兴衰'),
(2, '活着', '余华', '作家出版社', 39.50, 120, '讲述了农村人福贵悲惨的人生遭遇'),
(3, '经济学原理', '曼昆', '北京大学出版社', 88.00, 80, '经济学入门经典教材，深入浅出地讲解经济学基本原理'),
(4, '明朝那些事儿', '当年明月', '中国友谊出版公司', 328.00, 60, '以史料为基础，以小说的笔法讲述了明朝300年的历史');
