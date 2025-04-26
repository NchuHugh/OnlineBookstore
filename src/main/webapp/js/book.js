// js/book.js
// 负责首页图书列表加载和搜索功能

// 全局变量
let books = [];

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 加载图书列表
    loadBooks();
    
    // 绑定搜索按钮事件
    document.getElementById('searchBtn').addEventListener('click', function() {
        const keyword = document.getElementById('searchInput').value.trim();
        if (keyword) {
            searchBooks(keyword);
        } else {
            loadBooks();
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
 */
function loadBooks() {
    fetch('/api/book')
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                books = data.data;
                renderBooks(books);
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
 */
function searchBooks(keyword) {
    fetch(`/api/book?search=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                books = data.data;
                renderBooks(books);
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
        
        const coverUrl = book.coverImage || 'https://via.placeholder.com/150x200?text=No+Image';
        
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
