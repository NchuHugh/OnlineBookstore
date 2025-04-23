package com.hugh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC配置类
 */
@Configuration
@ComponentScan("com.hugh.controller")
@EnableWebMvc
public class SpringMVcConfig implements WebMvcConfigurer {
    
    /**
     * 配置静态资源处理
     * @param registry 资源处理器注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源处理
        // 根目录HTML文件
        registry.addResourceHandler("/*.html")
                .addResourceLocations("/");
        
        // CSS文件路径
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");
        
        // JavaScript文件路径
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");
        
        // 图片文件路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");
        
        // HTML页面路径
        registry.addResourceHandler("/pages/**")
                .addResourceLocations("/pages/");
    }
    
    /**
     * 配置视图解析器
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        return resolver;
    }
    
    /**
     * 启用默认Servlet处理器，处理静态资源
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
