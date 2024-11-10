package com.travelport.projectone.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${app.db.username}")
    private String username;

    @Value("${app.db.password}")
    private String password;

    @Value("${app.db.driverClassName}")
    private String driverClassName;

    @Value("${app.db.url}")
    private String url;

    @Bean
    public DataSource datasource() {

        var ds = new BasicDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasource());
        sessionFactory.setPackagesToScan("com.travelport.projectone.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public TransactionManager transactionManager() {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        var prop = new Properties();
        prop.put("hibernate.show_sql", "true");
//        prop.put("hibernate.hbm2ddl.auto", "none");
        return prop;
    }
}
