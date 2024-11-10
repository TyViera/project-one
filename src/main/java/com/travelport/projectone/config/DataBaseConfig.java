package com.travelport.projectone.config;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DataBaseConfig {

    @Value("${app.db.username}")
    private String username;

    @Value("${app.db.password}")
    private String password;

    @Value("${app.db.driverClassName}")
    private String driverClassName;

    @Value("${app.db.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        var ds = new BasicDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        return ds;
    }

//    @Bean
//    public DataSource dataSource(){
//        var ds = new BasicDataSource();
//        ds.setUsername("root");
//        ds.setPassword("my-secret-pw");
//        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        ds.setUrl("jdbc:mysql://localhost:3306/mydb1");
//        return ds;
//    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory() {
        var sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.travelport.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());

        // xml mapping - in the past xml files were used to map entities
        // sessionFactory.setMappingResources("com/travelport/entities/User.hbm.xml");

        // annotated mapping - in case you want to add more entities
        //sessionFactory.setAnnotatedClasses(User.class);
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
//        prop.put("hibernate.hbm2ddl.auto", "create");// validate, update, create, create-drop
        return prop;
    }
}
