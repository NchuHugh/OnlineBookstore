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
                if (callback) {
                    callback(data.data);
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
