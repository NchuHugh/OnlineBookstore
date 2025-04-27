// cart.js - 负责购物车页面的功能

// 全局变量
let cartItems = [];
let totalAmount = 0;

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 检查登录状态
    checkLoginStatus(function(user) {
        // 加载购物车数据
        loadCart();
    });
    
    // 绑定退出登录事件
    document.getElementById('logoutLink').addEventListener('click', handleLogout);
});

/**
 * 检查用户登录状态
 * @param {Function} callback 登录成功后的回调函数
 */
function checkLoginStatus(callback) {
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
                
                if (callback) {
                    callback(user);
                }
            } else {
                // 未登录，重定向到登录页
                window.location.href = 'login.html';
            }
        })
        .catch(error => {
            console.error('获取用户信息失败:', error);
            window.location.href = 'login.html';
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
            window.location.href = 'login.html';
        }
    })
    .catch(error => {
        console.error('退出登录失败:', error);
    });
}

/**
 * 加载购物车数据
 */
function loadCart() {
    fetch('/api/cart')
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const result = data.data;
                cartItems = result.items || [];
                totalAmount = result.totalAmount || 0;
                renderCart();
            } else {
                showEmptyCart(data.message || '购物车加载失败');
            }
        })
        .catch(error => {
            console.error('请求购物车数据失败:', error);
            showEmptyCart('网络错误，无法加载购物车');
        });
}

/**
 * 渲染购物车
 */
function renderCart() {
    const container = document.getElementById('cartContainer');
    container.innerHTML = '';
    
    if (cartItems.length === 0) {
        showEmptyCart('您的购物车是空的');
        return;
    }
    
    // 创建购物车头部
    const cartHeader = document.createElement('div');
    cartHeader.className = 'cart-header';
    
    const cartTitle = document.createElement('div');
    cartTitle.className = 'cart-title';
    cartTitle.textContent = '我的购物车';
    
    const cartActions = document.createElement('div');
    cartActions.className = 'cart-actions';
    
    const clearCartBtn = document.createElement('button');
    clearCartBtn.className = 'btn btn-danger';
    clearCartBtn.textContent = '清空购物车';
    clearCartBtn.addEventListener('click', clearCart);
    
    cartActions.appendChild(clearCartBtn);
    cartHeader.append(cartTitle, cartActions);
    
    // 创建购物车内容区域
    const cartGrid = document.createElement('div');
    cartGrid.className = 'cart-grid';
    
    // 创建购物车表格
    const cartTable = document.createElement('table');
    cartTable.className = 'cart-table';
    
    // 表头
    const thead = document.createElement('thead');
    thead.innerHTML = `
        <tr>
            <th>商品</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>操作</th>
        </tr>
    `;
    
    // 表体
    const tbody = document.createElement('tbody');
    
    cartItems.forEach(item => {
        const tr = document.createElement('tr');
        
        // 商品信息
        const tdProduct = document.createElement('td');
        tdProduct.style.display = 'flex';
        tdProduct.style.alignItems = 'center';
        tdProduct.style.gap = '1rem';
        
        const coverUrl = item.book.coverImage || '/image/book.jpg';
        tdProduct.innerHTML = `
            <img src="${coverUrl}" alt="${item.book.title}" class="cart-item-image">
            <div>
                <div class="cart-item-title">${item.book.title}</div>
                <div class="cart-item-author">作者: ${item.book.author}</div>
            </div>
        `;
        
        // 单价
        const tdPrice = document.createElement('td');
        tdPrice.className = 'cart-item-price';
        tdPrice.textContent = `¥${parseFloat(item.book.price).toFixed(2)}`;
        
        // 数量
        const tdQuantity = document.createElement('td');
        tdQuantity.innerHTML = `
            <div class="quantity-control">
                <button class="decrease-btn" data-id="${item.cartItemId}">-</button>
                <input type="number" value="${item.quantity}" min="1" max="${item.book.stock}" class="quantity-input" data-id="${item.cartItemId}">
                <button class="increase-btn" data-id="${item.cartItemId}" data-max="${item.book.stock}">+</button>
            </div>
        `;
        
        // 小计
        const tdSubtotal = document.createElement('td');
        tdSubtotal.className = 'cart-item-subtotal';
        const subtotal = (item.book.price * item.quantity).toFixed(2);
        tdSubtotal.textContent = `¥${subtotal}`;
        
        // 操作
        const tdActions = document.createElement('td');
        tdActions.className = 'cart-item-actions';
        tdActions.innerHTML = `
            <button class="remove-btn" data-id="${item.cartItemId}">删除</button>
        `;
        
        tr.append(tdProduct, tdPrice, tdQuantity, tdSubtotal, tdActions);
        tbody.appendChild(tr);
    });
    
    cartTable.append(thead, tbody);
    
    // 创建购物车结算区域
    const cartSummary = document.createElement('div');
    cartSummary.className = 'cart-summary';
    
    cartSummary.innerHTML = `
        <div class="cart-summary-row">
            <div class="cart-summary-label">商品总计</div>
            <div class="cart-summary-value">¥${parseFloat(totalAmount).toFixed(2)}</div>
        </div>
        <div class="cart-summary-row">
            <div class="cart-summary-label">运费</div>
            <div class="cart-summary-value">¥0.00</div>
        </div>
        <div class="cart-summary-row" style="border-top: 1px solid #eee; padding-top: 1rem;">
            <div class="cart-summary-label">应付总额</div>
            <div class="cart-summary-value">¥${parseFloat(totalAmount).toFixed(2)}</div>
        </div>
        <div class="cart-checkout">
            <button class="btn btn-primary" id="checkoutBtn">结算</button>
        </div>
    `;
    
    // 组装购物车页面
    cartGrid.append(cartTable, cartSummary);
    container.append(cartHeader, cartGrid);
    
    // 绑定事件
    bindCartEvents();
}

/**
 * 绑定购物车事件
 */
function bindCartEvents() {
    // 减少数量按钮
    document.querySelectorAll('.decrease-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const cartItemId = this.getAttribute('data-id');
            const input = document.querySelector(`.quantity-input[data-id="${cartItemId}"]`);
            let value = parseInt(input.value);
            
            if (value > 1) {
                value--;
                input.value = value;
                updateCartItemQuantity(cartItemId, value);
            }
        });
    });
    
    // 增加数量按钮
    document.querySelectorAll('.increase-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const cartItemId = this.getAttribute('data-id');
            const maxStock = parseInt(this.getAttribute('data-max'));
            const input = document.querySelector(`.quantity-input[data-id="${cartItemId}"]`);
            let value = parseInt(input.value);
            
            if (value < maxStock) {
                value++;
                input.value = value;
                updateCartItemQuantity(cartItemId, value);
            }
        });
    });
    
    // 数量输入框
    document.querySelectorAll('.quantity-input').forEach(input => {
        input.addEventListener('change', function() {
            const cartItemId = this.getAttribute('data-id');
            let value = parseInt(this.value);
            
            if (isNaN(value) || value < 1) {
                value = 1;
                this.value = value;
            } else if (value > parseInt(this.max)) {
                value = parseInt(this.max);
                this.value = value;
            }
            
            updateCartItemQuantity(cartItemId, value);
        });
    });
    
    // 删除按钮
    document.querySelectorAll('.remove-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const cartItemId = this.getAttribute('data-id');
            removeCartItem(cartItemId);
        });
    });
    
    // 结算按钮
    document.getElementById('checkoutBtn').addEventListener('click', function() {
        // 跳转到结算页面
        window.location.href = 'checkout.html';
    });
}

/**
 * 更新购物车项数量
 * @param {number} cartItemId 购物车项ID
 * @param {number} quantity 数量
 */
function updateCartItemQuantity(cartItemId, quantity) {
    fetch('/api/cart/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            cartItemId: cartItemId,
            quantity: quantity
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 重新加载购物车
            loadCart();
        } else {
            alert(data.message || '更新数量失败');
        }
    })
    .catch(error => {
        console.error('更新购物车失败:', error);
    });
}

/**
 * 从购物车中删除项
 * @param {number} cartItemId 购物车项ID
 */
function removeCartItem(cartItemId) {
    if (!confirm('确定要从购物车中删除此商品吗？')) {
        return;
    }
    
    fetch(`/api/cart/remove/${cartItemId}`, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 重新加载购物车
            loadCart();
        } else {
            alert(data.message || '删除失败');
        }
    })
    .catch(error => {
        console.error('删除购物车项失败:', error);
    });
}

/**
 * 清空购物车
 */
function clearCart() {
    if (!confirm('确定要清空购物车吗？此操作不可恢复。')) {
        return;
    }
    
    fetch('/api/cart/clear', {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 重新加载购物车
            loadCart();
        } else {
            alert(data.message || '清空购物车失败');
        }
    })
    .catch(error => {
        console.error('清空购物车失败:', error);
    });
}

/**
 * 显示空购物车
 * @param {string} message 提示信息
 */
function showEmptyCart(message) {
    const container = document.getElementById('cartContainer');
    container.innerHTML = `
        <div class="empty-cart">
            <div class="empty-cart-icon">🛒</div>
            <div class="empty-cart-message">${message}</div>
            <a href="index.html" class="btn btn-primary">去购物</a>
        </div>
    `;
}
