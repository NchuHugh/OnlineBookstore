package com.hugh.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.hugh.domain");
        // 设置XML映射文件的位置
        factoryBean.setMapperLocations(new org.springframework.core.io.ClassPathResource[] {
            new org.springframework.core.io.ClassPathResource("com/hugh/mapper/BookDao.xml"),
            new org.springframework.core.io.ClassPathResource("com/hugh/mapper/CartItemDao.xml"),
            new org.springframework.core.io.ClassPathResource("com/hugh/mapper/UserDao.xml"),
            new org.springframework.core.io.ClassPathResource("com/hugh/mapper/UserProfileDao.xml")
        });
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.hugh.mapper");
        return mapperScannerConfigurer;
    }
}
