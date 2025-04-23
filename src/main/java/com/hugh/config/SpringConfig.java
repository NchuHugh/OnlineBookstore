package com.hugh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan("com.hugh.service")
@Import({JDBcConfig.class,MyBatisConfig.class})
public class SpringConfig {
}
