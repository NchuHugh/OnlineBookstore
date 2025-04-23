// 用户个人资料页面的JavaScript函数

// 全局变量存储用户信息
let currentUser = null;

// 页面加载时检查登录状态并获取用户信息
document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus(function(user) {
        currentUser = user;
        displayUserInfo();
        loadUserProfile();
    });
    setupTabNavigation();
    
    // 绑定事件处理函数
    document.getElementById('updateProfileBtn').addEventListener('click', handleUpdateProfile);
    document.getElementById('changePasswordBtn').addEventListener('click', handleChangePassword);
    document.getElementById('logoutBtn').addEventListener('click', handleLogout);
});

// 显示用户基本信息
function displayUserInfo() {
    document.getElementById('usernameDisplay').textContent = currentUser.username;
    document.getElementById('emailDisplay').textContent = currentUser.email;
    
    // 改进日期处理逻辑
    try {
        // 检查createdAt是否为数字型时间戳
        let timestamp;
        if (typeof currentUser.createdAt === 'number') {
            timestamp = currentUser.createdAt;
        } else if (typeof currentUser.createdAt === 'string') {
            // 尝试解析字符串日期
            timestamp = Date.parse(currentUser.createdAt);
        } else {
            // 如果不是数字或字符串，使用当前时间
            timestamp = Date.now();
        }
        
        // 检查是否为有效时间戳
        if (isNaN(timestamp) || timestamp < 1000000000000) { // 小于2001年的时间戳可能是秒级时间戳
            // 如果是秒级时间戳，转换为毫秒级
            if (timestamp > 1000000000 && timestamp < 10000000000) {
                timestamp *= 1000;
            } else {
                // 如果无法解析，使用当前时间
                timestamp = Date.now();
            }
        }
        
        const createdAt = new Date(timestamp);
        document.getElementById('createdAtDisplay').textContent = createdAt.toLocaleString();
    } catch (e) {
        console.error('日期处理错误:', e);
        // 出错时使用当前时间
        document.getElementById('createdAtDisplay').textContent = new Date().toLocaleString();
    }
}

// 加载用户详细资料
function loadUserProfile() {
    // 使用完整的API路径
    const apiRoot = '/api';
    fetch(`${apiRoot}/users/${currentUser.userId}/profile`)
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                const profile = data.data;
                
                // 填充表单字段
                document.getElementById('realName').value = profile.realName || '';
                document.getElementById('address').value = profile.address || '';
                document.getElementById('phone').value = profile.phone || '';
                
                // 更新个人资料展示区域
                document.getElementById('realNameDisplay').textContent = profile.realName || '待完善';
                document.getElementById('addressDisplay').textContent = profile.address || '待完善';
                document.getElementById('phoneDisplay').textContent = profile.phone || '待完善';
                
                // 改进处理更新时间的逻辑
                if (profile.updatedAt) {
                    try {
                        // 检查updatedAt是否为数字型时间戳
                        let timestamp;
                        if (typeof profile.updatedAt === 'number') {
                            timestamp = profile.updatedAt;
                        } else if (typeof profile.updatedAt === 'string') {
                            // 尝试解析字符串日期
                            timestamp = Date.parse(profile.updatedAt);
                        } else {
                            // 如果不是数字或字符串，使用当前时间
                            document.getElementById('updatedAtDisplay').textContent = '尚未更新';
                            return;
                        }
                        
                        // 检查是否为有效时间戳
                        if (isNaN(timestamp) || timestamp < 1000000000000) { // 小于2001年的时间戳可能是秒级时间戳
                            // 如果是秒级时间戳，转换为毫秒级
                            if (timestamp > 1000000000 && timestamp < 10000000000) {
                                timestamp *= 1000;
                            } else {
                                // 如果无法解析或是1970年附近，显示尚未更新
                                document.getElementById('updatedAtDisplay').textContent = '尚未更新';
                                return;
                            }
                        }
                        
                        const updatedAt = new Date(timestamp);
                        document.getElementById('updatedAtDisplay').textContent = updatedAt.toLocaleString();
                    } catch (e) {
                        console.error('更新时间处理错误:', e);
                        document.getElementById('updatedAtDisplay').textContent = '尚未更新';
                    }
                } else {
                    document.getElementById('updatedAtDisplay').textContent = '尚未更新';
                }
            }
        })
        .catch(error => {
            console.error('获取用户详细资料失败:', error);
        });
}

// 设置标签页导航
function setupTabNavigation() {
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // 移除所有标签页和内容的活动状态
            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
            
            // 添加当前标签页和内容的活动状态
            this.classList.add('active');
            const tabId = this.getAttribute('data-tab');
            document.getElementById(tabId + 'Tab').classList.add('active');
        });
    });
}

// 处理更新用户详细资料
function handleUpdateProfile() {
    const realName = document.getElementById('realName').value;
    const address = document.getElementById('address').value;
    const phone = document.getElementById('phone').value;
    
    const profileData = {
        realName: realName,
        address: address,
        phone: phone
    };
    
    updateUserProfile(currentUser.userId, profileData);
    
    // 更新后重新加载个人资料显示
    setTimeout(() => {
        loadUserProfile();
    }, 1000);
}

// 处理修改密码
function handleChangePassword() {
    const oldPassword = document.getElementById('oldPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    changePassword(currentUser.userId, oldPassword, newPassword, confirmPassword);
}

// 处理退出登录
function handleLogout(e) {
    e.preventDefault();
    logoutUser();
}
