package com.travelport.projectone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.travelport.projectone")
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/clientSales").setViewName("clientSales");
        registry.addViewController("/clientSalesResult").setViewName("clientSalesResult");
        registry.addViewController("/crudClient").setViewName("crudClient");
        registry.addViewController("/crudProduct").setViewName("crudProduct");
        registry.addViewController("/productSales").setViewName("productSales");
        registry.addViewController("/productSalesResult").setViewName("productSalesResult");
        registry.addViewController("/sellProduct").setViewName("sellProduct");
    }

    @Bean
    public ViewResolver viewResolver() {
        var resolver = new org.springframework.web.servlet.view.InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}