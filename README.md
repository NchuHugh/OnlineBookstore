一、 项目概述

开发一个基础的网上书城系统，用户可以浏览图书、搜索图书、将图书加入购物车、下订单购买。管理员可以管理图书分类、图书信息、查看用户订单等。

二、 核心功能模块

用户模块 (User Module)

用户注册

用户登录/登出

用户信息查看与修改（基础信息、详细信息）

密码修改 (可选)

图书模块 (Book Module)

图书列表展示（可按分类）

图书搜索（按书名、作者等）

图书详情页展示

(后台) 图书信息管理 (CRUD -增删改查)

(后台) 图书分类管理 (CRUD - 增删改查)

购物车模块 (Shopping Cart Module)

添加图书到购物车

查看购物车

修改购物车中图书数量

从购物车删除图书

清空购物车

订单模块 (Order Module)

从购物车生成订单（结算）

查看我的订单列表

查看订单详情

(后台) 查看所有用户订单

(后台) 修改订单状态（例如：待支付、已支付、已发货、已完成）

三、 数据库设计 (关键表及关系)

这里设计几个核心表来说明关系：

t_user (用户表)

user_id (INT, PK, Auto Increment) - 用户ID (主键)

username (VARCHAR, Unique) - 用户名

password (VARCHAR) - 密码 (实际项目中应加密存储)

email (VARCHAR, Unique) - 邮箱

role (VARCHAR, Default 'customer') - 角色 ('customer', 'admin')

created_at (TIMESTAMP) - 创建时间

t_user_profile (用户详细信息表)

profile_id (INT, PK, Auto Increment) - 详情ID (主键)

user_id (INT, Unique, FK references t_user(user_id)) - 用户ID (外键, 且唯一)

real_name (VARCHAR, Nullable) - 真实姓名

address (VARCHAR, Nullable) - 收货地址

phone (VARCHAR, Nullable) - 联系电话

updated_at (TIMESTAMP) - 更新时间

关系：t_user 与 t_user_profile 是一对一 (1:1) 关系。 一个用户对应一条详细信息记录。通过在t_user_profile表上为user_id设置外键和唯一约束(UNIQUE)来实现。

t_category (图书分类表)

category_id (INT, PK, Auto Increment) - 分类ID (主键)

name (VARCHAR, Unique) - 分类名称

description (TEXT, Nullable) - 分类描述

t_book (图书表)

book_id (INT, PK, Auto Increment) - 图书ID (主键)

category_id (INT, FK references t_category(category_id)) - 分类ID (外键)

title (VARCHAR) - 书名

author (VARCHAR) - 作者

publisher (VARCHAR) - 出版社

price (DECIMAL(10, 2)) - 价格

stock (INT) - 库存

cover_image (VARCHAR, Nullable) - 封面图片路径

description (TEXT, Nullable) - 图书简介

关系：t_category 与 t_book 是一对多 (1:N) 关系。 一个分类下可以有多本图书。

t_order (订单表)

order_id (VARCHAR, PK) - 订单ID (主键, 可以使用UUID或时间戳+随机数生成)

user_id (INT, FK references t_user(user_id)) - 用户ID (外键)

order_time (TIMESTAMP) - 下单时间

total_amount (DECIMAL(10, 2)) - 订单总金额

status (VARCHAR) - 订单状态 ('pending', 'paid', 'shipped', 'completed', 'cancelled')

shipping_address (VARCHAR) - 收货地址 (下单时确定)

recipient_name (VARCHAR) - 收件人姓名

recipient_phone (VARCHAR) - 收件人电话

关系：t_user 与 t_order 是一对多 (1:N) 关系。 一个用户可以有多个订单。

t_order_item (订单项表)

item_id (INT, PK, Auto Increment) - 订单项ID (主键)

order_id (VARCHAR, FK references t_order(order_id)) - 订单ID (外键)

book_id (INT, FK references t_book(book_id)) - 图书ID (外键)

quantity (INT) - 购买数量

price (DECIMAL(10, 2)) - 购买时单价 (冗余存储，防止图书价格变动影响历史订单)

关系：t_order 与 t_order_item 是一对多 (1:N) 关系。 一个订单包含多个订单项（即购买了多种或多本图书）。

(注意：购物车功能通常可以通过 Session 或 Redis 实现，如果要求必须持久化，也可以设计 t_cart 和 t_cart_item 表，关系类似于订单和订单项，用户和购物车可以是 1:1 或 1:N（如果保留历史购物车）。为了适中复杂度，这里建议优先用 Session 实现购物车，订单部分体现数据库关系即可。如果老师要求购物车也持久化，再添加相应表。)

四、 技术选型

后端框架: Spring + SpringMVC + MyBatis (SSM)

前端技术: JSP + JSTL + EL (经典组合，易于与SSM集成) / 或 Thymeleaf / 或 HTML + CSS + JavaScript (jQuery/Vue.js - 如果想前后端分离)

数据库: MySQL 5.7+ 或 8.0 / 或 PostgreSQL

服务器: Tomcat 8.5+

构建工具: Maven / Gradle

依赖管理: Maven / Gradle

日志: Logback / Log4j2

分页插件: PageHelper (MyBatis常用分页插件)

五、 实现要点与难点

SSM整合: 配置Spring、SpringMVC、MyBatis的整合文件（XML或Java Config）。

事务管理: 使用Spring的声明式事务管理订单提交等关键操作。

MyBatis映射: 编写Mapper接口和对应的XML文件，处理SQL语句，特别是处理一对一和一对多关系的查询（使用 <association> 和 <collection> 标签）。

查询用户及其详细信息 (1:1)

查询分类及其下的图书 (1:N)

查询订单及其包含的订单项 (1:N)

查询用户及其所有订单 (1:N)

MVC流程: Controller接收请求，调用Service处理业务逻辑，Service调用Mapper操作数据库，最后返回视图或JSON数据。

用户认证与授权: 可以使用Spring Security（如果想深入学习，但可能增加复杂度）或简单的Session/拦截器机制实现登录验证和权限控制（区分普通用户和管理员）。

购物车逻辑: Session实现的话要注意Session管理；数据库实现则要注意表设计和操作原子性。

异常处理: 全局异常处理机制。

分页: 图书列表、订单列表等需要分页展示。

六、 建议开发步骤

环境搭建: 创建Maven/Gradle项目，引入SSM及相关依赖，配置web.xml、Spring配置文件、SpringMVC配置文件、MyBatis配置文件、数据库连接池等。

数据库设计与创建: 根据上述设计创建数据库表。

基础模块开发:

先开发 用户模块 (注册、登录、基础CRUD)，确保SSM整合跑通。

开发 分类管理模块 (后台)。

开发 图书管理模块 (后台CRUD，前台列表、详情)。

核心业务模块开发:

实现 购物车功能 (Session或数据库)。

实现 订单功能 (下单、查看订单、后台管理)，重点关注事务和多表操作。

完善与测试:

加入权限控制逻辑。

实现分页功能。

进行单元测试和集成测试。

完善前端页面样式和交互。

七、 可选扩展功能 (如果时间和精力允许)

图书评论功能

图书评分功能

用户地址管理（多个收货地址）

集成支付接口模拟

使用Ajax改善用户体验（如动态加载、异步添加到购物车）

更复杂的后台统计报表

