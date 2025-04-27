// book-detail.js - 负责图书详情页面的功能

// 全局变量
let book = null;
let quantity = 1;

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 获取URL参数中的图书ID
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');
    
    if (!bookId) {
        showError('未找到图书ID，请返回首页重新选择图书');
        return;
    }
    
    // 加载图书详情
    loadBookDetail(bookId);
    
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
 * 加载图书详情
 * @param {number} bookId 图书ID
 */
function loadBookDetail(bookId) {
    fetch(`/api/book/${bookId}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                book = data.data;
                renderBookDetail(book);
            } else {
                showError(data.message || '加载图书详情失败');
            }
        })
        .catch(error => {
            console.error('请求图书详情失败:', error);
            showError('网络错误，无法加载图书详情');
        });
}

/**
 * 渲染图书详情
 * @param {Object} book 图书数据
 */
function renderBookDetail(book) {
    const container = document.getElementById('bookDetailContainer');
    container.innerHTML = '';
    
    // 创建详情页面结构
    const detailDiv = document.createElement('div');
    detailDiv.className = 'book-detail';
    
    // 图书封面
    const coverUrl = book.coverImage || '/image/book.jpg';
    const imageDiv = document.createElement('div');
    imageDiv.className = 'book-image';
    imageDiv.innerHTML = `<img src="${coverUrl}" alt="${book.title}">`;
    
    // 图书信息
    const infoDiv = document.createElement('div');
    infoDiv.className = 'book-info';
    
    // 图书标题
    const titleDiv = document.createElement('div');
    titleDiv.className = 'book-title';
    titleDiv.textContent = book.title;
    
    // 作者
    const authorDiv = document.createElement('div');
    authorDiv.className = 'book-author';
    authorDiv.textContent = `作者: ${book.author}`;
    
    // 出版社
    const publisherDiv = document.createElement('div');
    publisherDiv.className = 'book-publisher';
    publisherDiv.textContent = `出版社: ${book.publisher}`;
    
    // 价格
    const priceDiv = document.createElement('div');
    priceDiv.className = 'book-price';
    priceDiv.textContent = `¥${parseFloat(book.price).toFixed(2)}`;
    
    // 库存
    const stockDiv = document.createElement('div');
    stockDiv.className = 'book-stock';
    stockDiv.textContent = book.stock > 0 ? `库存: ${book.stock}` : '库存不足';
    
    // 描述
    const descDiv = document.createElement('div');
    descDiv.className = 'book-description';
    descDiv.textContent = book.description || '暂无描述';
    
    // 数量控制
    const quantityDiv = document.createElement('div');
    quantityDiv.className = 'quantity-control';
    quantityDiv.innerHTML = `
        <span>数量: </span>
        <button id="decreaseBtn">-</button>
        <input type="number" id="quantityInput" value="1" min="1" max="${book.stock}">
        <button id="increaseBtn">+</button>
    `;
    
    // 操作按钮
    const actionsDiv = document.createElement('div');
    actionsDiv.className = 'book-actions';
    
    const addToCartBtn = document.createElement('button');
    addToCartBtn.className = 'btn btn-primary';
    addToCartBtn.textContent = '加入购物车';
    addToCartBtn.disabled = book.stock <= 0;
    addToCartBtn.addEventListener('click', () => addToCart(book.bookId, quantity));
    
    actionsDiv.appendChild(addToCartBtn);
    
    // 组装详情页
    infoDiv.append(titleDiv, authorDiv, publisherDiv, priceDiv, stockDiv, descDiv, quantityDiv, actionsDiv);
    detailDiv.append(imageDiv, infoDiv);
    container.appendChild(detailDiv);
    
    // 绑定数量控制事件
    document.getElementById('quantityInput').addEventListener('change', handleQuantityChange);
    document.getElementById('decreaseBtn').addEventListener('click', decreaseQuantity);
    document.getElementById('increaseBtn').addEventListener('click', increaseQuantity);
}

/**
 * 处理数量变化
 */
function handleQuantityChange() {
    const input = document.getElementById('quantityInput');
    let value = parseInt(input.value);
    
    // 确保数量在有效范围内
    if (isNaN(value) || value < 1) {
        value = 1;
    } else if (value > book.stock) {
        value = book.stock;
    }
    
    input.value = value;
    quantity = value;
}

/**
 * 减少数量
 */
function decreaseQuantity() {
    const input = document.getElementById('quantityInput');
    let value = parseInt(input.value);
    
    if (value > 1) {
        input.value = value - 1;
        quantity = value - 1;
    }
}

/**
 * 增加数量
 */
function increaseQuantity() {
    const input = document.getElementById('quantityInput');
    let value = parseInt(input.value);
    
    if (value < book.stock) {
        input.value = value + 1;
        quantity = value + 1;
    }
}

/**
 * 添加到购物车
 * @param {number} bookId 图书ID
 * @param {number} quantity 数量
 */
function addToCart(bookId, quantity) {
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
                        quantity: quantity
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

/**
 * 显示错误信息
 * @param {string} message 错误信息
 */
function showError(message) {
    const container = document.getElementById('bookDetailContainer');
    container.innerHTML = `<div class="error">${message}</div>`;
}
