package com.hugh.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Servlet配置类
 * 用于初始化Spring MVC容器和配置Servlet映射
 */
public class ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 返回根应用上下文配置类
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * 返回Servlet应用上下文配置类
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVcConfig.class};
    }

    /**
     * 返回Servlet映射路径
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    
    /**
     * 配置过滤器
     */
    @Override
    protected Filter[] getServletFilters() {
        // 设置字符编码过滤器
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return new Filter[]{filter};
    }
}