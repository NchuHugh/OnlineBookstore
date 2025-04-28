package com.hugh.aop;

import com.hugh.controller.Result;
import com.hugh.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录检查切面
 * 用于处理需要登录的接口的权限检查
 */
@Aspect
@Component
public class LoginCheckAspect {

    /**
     * 定义切点：所有需要登录检查的方法
     * 这里使用注解作为标记，方便灵活配置哪些方法需要登录检查
     */
    @Pointcut("@annotation(com.hugh.aop.RequireLogin)")
    public void loginCheckPointcut() {
    }

    /**
     * 环绕通知：在目标方法执行前后添加登录检查逻辑
     * @param joinPoint 连接点
     * @return 返回目标方法的执行结果或未登录的错误信息
     * @throws Throwable 可能抛出的异常
     */
    @Around("loginCheckPointcut()")
    public Object aroundLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Result.error("系统错误");
        }
        
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        
        // 检查用户是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("未登录");
        }
        
        // 用户已登录，继续执行目标方法
        return joinPoint.proceed();
    }
}
