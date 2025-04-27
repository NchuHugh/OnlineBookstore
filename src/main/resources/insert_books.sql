-- 插入更多图书数据
USE onlinebookstore;

-- 确保分类存在
INSERT IGNORE INTO t_category (name, description) VALUES
('科幻小说', '科幻类小说和作品'),
('编程语言', '各类编程语言学习书籍'),
('人文社科', '人文、社会科学类书籍'),
('生活艺术', '生活方式、艺术欣赏类书籍');

-- 插入更多图书
INSERT INTO t_book (category_id, title, author, publisher, price, stock, cover_image, description) VALUES
-- 计算机科学类图书
(1, 'C++ Primer', 'Stanley B. Lippman', '电子工业出版社', 99.00, 50, 'https://img1.doubanio.com/view/subject/s/public/s28323106.jpg', 'C++编程必读经典，全面系统地介绍C++语言的特性、标准库和编程技术'),
(1, '算法导论', 'Thomas H. Cormen', '机械工业出版社', 128.00, 30, 'https://img9.doubanio.com/view/subject/s/public/s25648004.jpg', '算法领域的经典教材，全面介绍了计算机算法的设计与分析'),
(1, '深入理解计算机系统', 'Randal E. Bryant', '机械工业出版社', 139.00, 25, 'https://img9.doubanio.com/view/subject/s/public/s29195878.jpg', '计算机科学经典教材，从程序员的视角详细阐述计算机系统的本质概念'),

-- 文学小说类图书
(2, '百年孤独', '加西亚·马尔克斯', '南海出版公司', 55.00, 100, 'https://img2.doubanio.com/view/subject/s/public/s27237850.jpg', '拉丁美洲魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事'),
(2, '三体', '刘慈欣', '重庆出版社', 68.00, 150, 'https://img9.doubanio.com/view/subject/s/public/s29634528.jpg', '中国科幻小说代表作，讲述了地球人类文明与三体文明的信息交流、生死搏杀及两个文明的兴衰历程'),
(2, '1984', '乔治·奥威尔', '北京十月文艺出版社', 45.00, 80, 'https://img1.doubanio.com/view/subject/s/public/s4371408.jpg', '著名反乌托邦小说，描述了一个极权主义社会中的生活'),

-- 经济管理类图书
(3, '小狗钱钱', '博多·舍费尔', '中信出版社', 36.00, 120, 'https://img9.doubanio.com/view/subject/s/public/s29634197.jpg', '以童话故事的形式讲述了理财知识，深受读者喜爱的财商教育书籍'),
(3, '穷爸爸富爸爸', '罗伯特·清崎', '世界图书出版公司', 49.00, 90, 'https://img3.doubanio.com/view/subject/s/public/s27274192.jpg', '畅销全球的财商教育书籍，讲述了作者从两个爸爸身上学到的关于金钱和投资的智慧'),
(3, '金字塔原理', '芭芭拉·明托', '南海出版公司', 59.90, 40, 'https://img1.doubanio.com/view/subject/s/public/s28339237.jpg', '思考、表达和解决问题的逻辑，商业写作与沟通的经典之作'),

-- 历史文化类图书
(4, '人类简史', '尤瓦尔·赫拉利', '中信出版社', 68.00, 70, 'https://img3.doubanio.com/view/subject/s/public/s27814883.jpg', '从认知革命、农业革命、科学革命到人工智能，重新解读人类发展史'),
(4, '万历十五年', '黄仁宇', '中华书局', 39.00, 60, 'https://img1.doubanio.com/view/subject/s/public/s1790028.jpg', '以万历十五年(1587年)为中心，描绘了明代社会的全景图像'),
(4, '中国哲学简史', '冯友兰', '北京大学出版社', 38.00, 50, 'https://img1.doubanio.com/view/subject/s/public/s1045511.jpg', '系统介绍中国哲学发展的经典著作，深入浅出地阐述了中国哲学的精髓'),

-- 科幻小说类图书
(5, '银河帝国', '艾萨克·阿西莫夫', '江苏凤凰文艺出版社', 328.00, 40, 'https://img9.doubanio.com/view/subject/s/public/s33633676.jpg', '科幻文学经典，描绘了人类在银河系建立帝国并最终衰落的宏大历史'),
(5, '沙丘', '弗兰克·赫伯特', '江苏凤凰文艺出版社', 62.00, 35, 'https://img2.doubanio.com/view/subject/s/public/s33561952.jpg', '被誉为"科幻小说圣经"，讲述了在遥远的未来，围绕着控制宝贵资源香料的政治、宗教斗争'),
(5, '神经漫游者', '威廉·吉布森', '四川科学技术出版社', 45.00, 30, 'https://img2.doubanio.com/view/subject/s/public/s29350294.jpg', '赛博朋克科幻小说开山之作，描述了一个被计算机网络和人工智能主宰的未来世界'),

-- 编程语言类图书
(6, 'JavaScript高级程序设计', 'Nicholas C. Zakas', '人民邮电出版社', 129.00, 60, 'https://img3.doubanio.com/view/subject/s/public/s28315289.jpg', 'JavaScript领域的经典之作，全面介绍了JavaScript语言的核心概念、DOM、BOM等内容'),
(6, 'Go语言实战', 'William Kennedy', '人民邮电出版社', 79.00, 45, 'https://img9.doubanio.com/view/subject/s/public/s29434001.jpg', '全面讲解Go语言的特性，并通过实例展示如何使用Go语言构建真实世界的应用程序'),
(6, 'Python编程：从入门到实践（第2版）', 'Eric Matthes', '人民邮电出版社', 89.00, 80, 'https://img9.doubanio.com/view/subject/s/public/s33978679.jpg', '零基础入门Python的优秀教材，通过丰富的示例引导读者循序渐进地掌握Python编程'),

-- 人文社科类图书
(7, '乌合之众', '古斯塔夫·勒庞', '中央编译出版社', 38.00, 70, 'https://img2.doubanio.com/view/subject/s/public/s1988393.jpg', '群体心理学的奠基之作，探讨了群体行为的特点和规律'),
(7, '枪炮、病菌与钢铁', '贾雷德·戴蒙德', '上海译文出版社', 59.80, 40, 'https://img1.doubanio.com/view/subject/s/public/s1738643.jpg', '探讨人类社会发展的不平等历史，揭示了地理、生物和环境因素对人类文明发展的影响'),
(7, '思考，快与慢', '丹尼尔·卡尼曼', '中信出版社', 69.00, 55, 'https://img3.doubanio.com/view/subject/s/public/s10345719.jpg', '诺贝尔经济学奖得主的心理学著作，揭示了人类思考的两种模式及其对决策的影响'),

-- 生活艺术类图书
(8, '菜谱大全', '萨巴蒂娜', '中国轻工业出版社', 78.00, 100, 'https://img1.doubanio.com/view/subject/s/public/s28296550.jpg', '收录了各国经典菜肴的制作方法，图文并茂，适合家庭烹饪爱好者'),
(8, '艺术的故事', '贡布里希', '广西美术出版社', 128.00, 30, 'https://img3.doubanio.com/view/subject/s/public/s3219163.jpg', '西方艺术史的经典著作，以通俗易懂的语言讲述了从史前到现代的艺术发展历程'),
(8, '园艺造景', '约翰·布鲁克斯', '中国建筑工业出版社', 88.00, 25, 'https://img2.doubanio.com/view/subject/s/public/s4146437.jpg', '园艺设计和景观规划的实用指南，包含了丰富的案例和实用技巧'),

(1, '活着', '余华', '作家出版社', 46.00, 95, 'https://covers.openlibrary.org/b/id/10594758-L.jpg', '一段普通人跌宕起伏的生命历程。'),
(1, '白夜行', '东野圭吾', '南海出版公司', 59.00, 85, 'https://covers.openlibrary.org/b/id/10624758-L.jpg', '充满阴谋与秘密的跨越岁月的推理故事。'),
(1, '恶意', '东野圭吾', '南海出版公司', 39.00, 60, 'https://covers.openlibrary.org/b/id/10654758-L.jpg', '对人性深层次探究的推理佳作。'),

-- 历史
(2, '全球通史', '斯塔夫里阿诺斯', '北京大学出版社', 89.00, 70, 'https://covers.openlibrary.org/b/id/10594759-L.jpg', '从史前到21世纪的人类文明发展全景。'),
(2, '战争与和平', '列夫·托尔斯泰', '人民文学出版社', 99.00, 50, 'https://covers.openlibrary.org/b/id/10614759-L.jpg', '以俄罗斯历史为背景描绘的史诗巨著。'),

-- 科技
(3, '深度学习', 'Ian Goodfellow', '人民邮电出版社', 158.00, 35, 'https://covers.openlibrary.org/b/id/10584758-L.jpg', '人工智能和机器学习领域权威之作。'),
(3, '计算机程序的构造和解释', 'Harold Abelson', '机械工业出版社', 135.00, 20, 'https://covers.openlibrary.org/b/id/10634758-L.jpg', 'SICP经典教材，程序员的思维指南。'),

-- 艺术
(4, '当代艺术史', 'Terry Smith', '广西师范大学出版社', 88.00, 25, 'https://covers.openlibrary.org/b/id/10574758-L.jpg', '系统梳理20世纪以来的艺术演变。'),
(4, '摄影笔记', '森山大道', '新星出版社', 65.00, 30, 'https://covers.openlibrary.org/b/id/10664758-L.jpg', '知名街头摄影师的实践记录。'),

-- 儿童
(5, '魔法灰姑娘', '盖尔·卡森·莱文', '新星出版社', 48.00, 120, 'https://covers.openlibrary.org/b/id/10564758-L.jpg', '一个与众不同的童话公主成长故事。'),
(5, '秘密花园', '弗朗西丝·霍奇森·伯内特', '译林出版社', 45.00, 90, 'https://covers.openlibrary.org/b/id/10674758-L.jpg', '关于成长与治愈的温暖故事。'),

-- 心理学
(6, '蛤蟆先生去看心理医生', '罗伯特·戴博德', '中信出版社', 49.00, 70, 'https://covers.openlibrary.org/b/id/10554758-L.jpg', '以寓言形式讲述心理治疗的过程。'),
(6, '思考，快与慢', '丹尼尔·卡尼曼', '中信出版社', 88.00, 60, 'https://covers.openlibrary.org/b/id/10684758-L.jpg', '揭示人类决策过程中的两种思维系统。'),

-- 哲学
(7, '沉思录', '马可·奥勒留', '商务印书馆', 35.00, 45, 'https://covers.openlibrary.org/b/id/10544758-L.jpg', '斯多葛主义哲学的杰作。'),
(7, '尼采：悲剧的诞生', '弗里德里希·尼采', '译林出版社', 49.00, 30, 'https://covers.openlibrary.org/b/id/10694758-L.jpg', '探讨艺术、悲剧与文化起源的哲学思考。'),

-- 旅行
(8, '在路上', '杰克·凯鲁亚克', '作家出版社', 66.00, 40, 'https://covers.openlibrary.org/b/id/10534758-L.jpg', '垮掉的一代精神象征，公路旅行传奇。'),
(8, '孤独星球：意大利', 'Lonely Planet', '中国地图出版社', 128.00, 22, 'https://covers.openlibrary.org/b/id/10634759-L.jpg', '详尽的意大利旅行指南。'),

-- 自我提升
(9, '掌控习惯', '詹姆斯·克利尔', '中信出版社', 68.00, 85, 'https://covers.openlibrary.org/b/id/10524758-L.jpg', '微小习惯成就巨大改变。'),
(9, '刻意练习', '安德斯·艾利克森', '机械工业出版社', 72.00, 65, 'https://covers.openlibrary.org/b/id/10604758-L.jpg', '技能成长与卓越表现背后的科学。'),

-- 经济管理
(10, '从0到1', '彼得·蒂尔', '中信出版社', 58.00, 90, 'https://covers.openlibrary.org/b/id/10514758-L.jpg', '创新创业的思维指南。'),
(10, '黑天鹅', '纳西姆·尼古拉斯·塔勒布', '中信出版社', 88.00, 50, 'https://covers.openlibrary.org/b/id/10614758-L.jpg', '探索极端事件与不确定性对世界的影响。');
