// js/book.js
// 负责首页图书列表加载和搜索功能

// 全局变量
let books = [];
let currentPage = 1;
let pageSize = 16;
let totalPages = 1;
let currentKeyword = '';

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 加载图书列表
    loadBooks(currentPage, pageSize);
    
    // 绑定搜索按钮事件
    document.getElementById('searchBtn').addEventListener('click', function() {
        const keyword = document.getElementById('searchInput').value.trim();
        currentKeyword = keyword;
        currentPage = 1; // 搜索时重置到第一页
        if (keyword) {
            searchBooks(keyword, currentPage, pageSize);
        } else {
            loadBooks(currentPage, pageSize);
        }
    });
    
    // 回车键也触发搜索
    document.getElementById('searchInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            document.getElementById('searchBtn').click();
        }
    });
    
    // 检查登录状态
    checkLoginStatus();
    
    // 绑定退出登录事件
    document.getElementById('logoutLink').addEventListener('click', handleLogout);
});

/**
 * 检查用户登录状态
 */
function checkLoginStatus() {
    fetch('/api/users/current')
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                // 用户已登录
                const user = data.data;
                document.getElementById('userWelcome').textContent = `${user.username}，您好！`;
                document.getElementById('loginLink').style.display = 'none';
                document.getElementById('registerLink').style.display = 'none';
                document.getElementById('profileLink').style.display = 'inline';
                document.getElementById('logoutLink').style.display = 'inline';
            }
        })
        .catch(error => {
            console.error('获取用户信息失败:', error);
        });
}

/**
 * 处理退出登录
 */
function handleLogout(e) {
    e.preventDefault();

    fetch('/api/users/logout', {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            window.location.reload();
        }
    })
    .catch(error => {
        console.error('退出登录失败:', error);
    });
}

/**
 * 加载所有图书
 * @param {number} page 页码
 * @param {number} size 每页数量
 */
function loadBooks(page = 1, size = 16) {
    fetch(`/api/book?page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const result = data.data;
                books = result.books;
                currentPage = result.page;
                totalPages = result.totalPages;
                
                renderBooks(books);
                renderPagination(currentPage, totalPages);
            } else {
                console.error('加载图书失败:', data.message);
            }
        })
        .catch(error => {
            console.error('请求图书数据失败:', error);
        });
}

/**
 * 搜索图书
 * @param {string} keyword 搜索关键词
 * @param {number} page 页码
 * @param {number} size 每页数量
 */
function searchBooks(keyword, page = 1, size = 16) {
    fetch(`/api/book?search=${encodeURIComponent(keyword)}&page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const result = data.data;
                books = result.books;
                currentPage = result.page;
                totalPages = result.totalPages;
                
                renderBooks(books);
                renderPagination(currentPage, totalPages, keyword);
            } else {
                console.error('搜索图书失败:', data.message);
            }
        })
        .catch(error => {
            console.error('搜索请求失败:', error);
        });
}

/**
 * 渲染图书列表
 * @param {Array} bookList 图书列表数据
 */
function renderBooks(bookList) {
    const container = document.getElementById('bookList');
    container.innerHTML = '';
    
    if (bookList.length === 0) {
        container.innerHTML = '<p style="text-align:center;padding:2rem;">没有找到相关图书</p>';
        return;
    }
    
    bookList.forEach(book => {
        const bookCard = document.createElement('div');
        bookCard.className = 'book-card';
        
        const coverUrl = book.coverImage || '/image/book.jpg';
        
        bookCard.innerHTML = `
            <img src="${coverUrl}" alt="${book.title}">
            <div class="book-title">${book.title}</div>
            <div class="book-author">${book.author}</div>
            <div class="book-price">¥${parseFloat(book.price).toFixed(2)}</div>
            <div class="book-actions">
                <button class="btn btn-secondary view-detail" data-id="${book.bookId}">查看详情</button>
                <button class="btn btn-primary add-to-cart" data-id="${book.bookId}">加入购物车</button>
            </div>
        `;
        
        container.appendChild(bookCard);
    });
    
    // 绑定详情和购物车按钮事件
    document.querySelectorAll('.view-detail').forEach(btn => {
        btn.addEventListener('click', function() {
            const bookId = this.getAttribute('data-id');
            window.location.href = `book-detail.html?id=${bookId}`;
        });
    });
    
    document.querySelectorAll('.add-to-cart').forEach(btn => {
        btn.addEventListener('click', function() {
            const bookId = this.getAttribute('data-id');
            addToCart(bookId);
        });
    });
}

/**
 * 渲染分页器
 * @param {number} currentPage 当前页码
 * @param {number} totalPages 总页数
 * @param {string} keyword 搜索关键词（可选）
 */
function renderPagination(currentPage, totalPages, keyword = '') {
    // 检查是否已经存在分页器
    let paginationContainer = document.getElementById('pagination');
    
    if (!paginationContainer) {
        // 如果不存在，创建分页器容器
        paginationContainer = document.createElement('div');
        paginationContainer.id = 'pagination';
        paginationContainer.className = 'pagination';
        
        // 将分页器添加到图书列表下方
        const bookListContainer = document.getElementById('bookList').parentNode;
        bookListContainer.appendChild(paginationContainer);
        
        // 添加分页器样式
        const style = document.createElement('style');
        style.textContent = `
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 2rem;
                gap: 0.5rem;
            }
            .pagination button {
                padding: 0.5rem 1rem;
                border: 1px solid #ddd;
                background-color: white;
                cursor: pointer;
                border-radius: 3px;
            }
            .pagination button.active {
                background-color: #4CAF50;
                color: white;
                border-color: #4CAF50;
            }
            .pagination button:hover:not(.active) {
                background-color: #f1f1f1;
            }
            .pagination button:disabled {
                color: #ccc;
                cursor: not-allowed;
            }
        `;
        document.head.appendChild(style);
    }
    
    // 清空分页器
    paginationContainer.innerHTML = '';
    
    // 如果只有1页，不显示分页器
    if (totalPages <= 1) return;
    
    // 上一页按钮
    const prevButton = document.createElement('button');
    prevButton.textContent = '上一页';
    prevButton.disabled = currentPage <= 1;
    prevButton.addEventListener('click', () => {
        if (currentPage > 1) {
            if (keyword) {
                searchBooks(keyword, currentPage - 1, pageSize);
            } else {
                loadBooks(currentPage - 1, pageSize);
            }
        }
    });
    paginationContainer.appendChild(prevButton);
    
    // 页码按钮
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);
    
    // 调整范围，确保显示5个页码按钮（如果有足够页码）
    if (endPage - startPage < 4 && totalPages > 5) {
        if (startPage === 1) {
            endPage = Math.min(5, totalPages);
        } else if (endPage === totalPages) {
            startPage = Math.max(1, totalPages - 4);
        }
    }
    
    // 创建页码按钮
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        if (i === currentPage) {
            pageButton.className = 'active';
        }
        pageButton.addEventListener('click', () => {
            if (i !== currentPage) {
                if (keyword) {
                    searchBooks(keyword, i, pageSize);
                } else {
                    loadBooks(i, pageSize);
                }
            }
        });
        paginationContainer.appendChild(pageButton);
    }
    
    // 下一页按钮
    const nextButton = document.createElement('button');
    nextButton.textContent = '下一页';
    nextButton.disabled = currentPage >= totalPages;
    nextButton.addEventListener('click', () => {
        if (currentPage < totalPages) {
            if (keyword) {
                searchBooks(keyword, currentPage + 1, pageSize);
            } else {
                loadBooks(currentPage + 1, pageSize);
            }
        }
    });
    paginationContainer.appendChild(nextButton);
}

/**
 * 添加到购物车
 * @param {number} bookId 图书ID
 */
function addToCart(bookId) {
    // 检查是否登录
    fetch('/api/users/current')
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                // 已登录，添加到购物车
                return fetch('/api/cart/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        bookId: bookId,
                        quantity: 1
                    })
                });
            } else {
                // 未登录，跳转到登录页
                alert('请先登录再添加商品到购物车');
                window.location.href = 'login.html';
                throw new Error('未登录');
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('已成功添加到购物车');
            } else {
                alert('添加失败: ' + data.message);
            }
        })
        .catch(error => {
            if (error.message !== '未登录') {
                console.error('添加购物车失败:', error);
            }
        });
}
