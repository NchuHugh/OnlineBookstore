// 用户认证相关的JavaScript函数

// 用户注册
function registerUser() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;
    const realName = document.getElementById('realName')?.value;
    const address = document.getElementById('address')?.value;
    const phone = document.getElementById('phone')?.value;
    
    // 简单验证
    if (!username || !password || !email) {
        document.getElementById('message').className = 'error';
        document.getElementById('message').textContent = '用户名、密码和邮箱为必填项！';
        return;
    }
    
    // 构建请求数据
    const requestData = {
        username: username,
        password: password,
        email: email
    };
    
    // 如果填写了个人资料，则添加到请求中
    if (realName || address || phone) {
        requestData.profile = {
            realName: realName,
            address: address,
            phone: phone
        };
    }
    
    // 发送注册请求
    fetch('/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            document.getElementById('message').className = 'success';
            document.getElementById('message').textContent = data.message;
            // 注册成功后跳转到登录页
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
        } else {
            document.getElementById('message').className = 'error';
            document.getElementById('message').textContent = data.message;
        }
    })
    .catch(error => {
        document.getElementById('message').className = 'error';
        document.getElementById('message').textContent = '注册请求失败：' + error.message;
    });
}

// 用户登录
function loginUser() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // 简单验证
    if (!username || !password) {
        document.getElementById('message').className = 'error';
        document.getElementById('message').textContent = '用户名和密码为必填项！';
        return;
    }
    
    // 构建请求数据
    const requestData = {
        username: username,
        password: password
    };
    
    // 发送登录请求
    fetch('/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            document.getElementById('message').className = 'success';
            document.getElementById('message').textContent = data.message;
            // 登录成功后跳转到首页
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 1000);
        } else {
            document.getElementById('message').className = 'error';
            document.getElementById('message').textContent = data.message;
        }
    })
    .catch(error => {
        document.getElementById('message').className = 'error';
        document.getElementById('message').textContent = '登录请求失败：' + error.message;
    });
}

// 退出登录
function logoutUser() {
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

// 检查用户登录状态
function checkLoginStatus(callback) {
    fetch('/api/users/current')
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                // 用户已登录
                const user = data.data;
                // 更新导航栏显示
                if (document.getElementById('userWelcome')) {
                    document.getElementById('userWelcome').textContent = `${user.username}，您好！`;
                }
                if (document.getElementById('loginLink')) {
                    document.getElementById('loginLink').style.display = 'none';
                }
                if (document.getElementById('registerLink')) {
                    document.getElementById('registerLink').style.display = 'none';
                }
                if (document.getElementById('profileLink')) {
                    document.getElementById('profileLink').style.display = 'inline';
                }
                if (document.getElementById('logoutLink')) {
                    document.getElementById('logoutLink').style.display = 'inline';
                    // 绑定退出登录事件
                    document.getElementById('logoutLink').addEventListener('click', function(e) {
                        e.preventDefault();
                        logoutUser();
                    });
                }
                
                // 获取购物车数量
                updateCartBadge();
                
                if (callback) {
                    callback(user);
                }
            } else {
                // 未登录，如果当前页面不是登录或注册页面，则重定向到登录页
                const currentPage = window.location.pathname.split('/').pop();
                if (currentPage !== 'login.html' && currentPage !== 'register.html') {
                    window.location.href = 'login.html';
                }
            }
        })
        .catch(error => {
            console.error('获取用户信息失败:', error);
            // 仅在非登录/注册页面重定向
            const currentPage = window.location.pathname.split('/').pop();
            if (currentPage !== 'login.html' && currentPage !== 'register.html') {
                window.location.href = 'login.html';
            }
        });
}

// 更新购物车数量显示
function updateCartBadge() {
    const cartLink = document.getElementById('cartLink');
    if (!cartLink) return;
    
    fetch('/api/cart')
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const items = data.data.items || [];
                if (items.length > 0) {
                    // 移除旧的徽章（如果存在）
                    const oldBadge = cartLink.querySelector('.cart-badge');
                    if (oldBadge) {
                        oldBadge.remove();
                    }
                    
                    // 添加新的徽章
                    const badge = document.createElement('span');
                    badge.className = 'cart-badge';
                    badge.textContent = items.length;
                    cartLink.appendChild(badge);
                }
            }
        })
        .catch(error => {
            console.error('获取购物车数据失败:', error);
        });
}

// 页面加载时检查登录状态并更新UI
document.addEventListener('DOMContentLoaded', function() {
    // 获取当前用户信息
    fetch('/api/users/current')
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                // 用户已登录
                const user = data.data;
                if (document.getElementById('userWelcome')) {
                    document.getElementById('userWelcome').textContent = `${user.username}，您好！`;
                }
                if (document.getElementById('loginLink')) {
                    document.getElementById('loginLink').style.display = 'none';
                }
                if (document.getElementById('registerLink')) {
                    document.getElementById('registerLink').style.display = 'none';
                }
                if (document.getElementById('profileLink')) {
                    document.getElementById('profileLink').style.display = 'inline';
                }
                if (document.getElementById('logoutLink')) {
                    document.getElementById('logoutLink').style.display = 'inline';
                    // 绑定退出登录事件
                    document.getElementById('logoutLink').addEventListener('click', function(e) {
                        e.preventDefault();
                        logoutUser();
                    });
                }
                
                // 更新购物车数量
                updateCartBadge();
            }
        })
        .catch(error => {
            console.error('获取用户信息失败:', error);
        });
});

// 更新用户详细资料
function updateUserProfile(userId, profileData) {
    fetch(`/api/users/${userId}/profile`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(profileData)
    })
    .then(response => response.json())
    .then(data => {
        const messageElement = document.getElementById('profileMessage');
        if (data.success) {
            messageElement.className = 'success';
            messageElement.textContent = data.message;
        } else {
            messageElement.className = 'error';
            messageElement.textContent = data.message;
        }
    })
    .catch(error => {
        document.getElementById('profileMessage').className = 'error';
        document.getElementById('profileMessage').textContent = '更新资料失败：' + error.message;
    });
}

// 修改密码
function changePassword(userId, oldPassword, newPassword, confirmPassword) {
    // 简单验证
    if (!oldPassword || !newPassword || !confirmPassword) {
        document.getElementById('passwordMessage').className = 'error';
        document.getElementById('passwordMessage').textContent = '所有密码字段都必须填写！';
        return;
    }
    
    if (newPassword !== confirmPassword) {
        document.getElementById('passwordMessage').className = 'error';
        document.getElementById('passwordMessage').textContent = '新密码与确认密码不匹配！';
        return;
    }
    
    const passwordData = {
        oldPassword: oldPassword,
        newPassword: newPassword
    };
    
    fetch(`/api/users/${userId}/password`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(passwordData)
    })
    .then(response => response.json())
    .then(data => {
        const messageElement = document.getElementById('passwordMessage');
        if (data.success) {
            messageElement.className = 'success';
            messageElement.textContent = data.message;
            // 清空密码字段
            document.getElementById('oldPassword').value = '';
            document.getElementById('newPassword').value = '';
            document.getElementById('confirmPassword').value = '';
        } else {
            messageElement.className = 'error';
            messageElement.textContent = data.message;
        }
    })
    .catch(error => {
        document.getElementById('passwordMessage').className = 'error';
        document.getElementById('passwordMessage').textContent = '修改密码失败：' + error.message;
    });
}
