package com.travelport.projectone.config;

import java.util.Properties;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
  public DataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setDriverClassName(driverClassName);
    ds.setUrl(url);
    return ds;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

    // Set the data source
    em.setDataSource(dataSource());

    // Specify the package where your entities are located
    em.setPackagesToScan("com.travelport.projectone.entities");

    // Set Hibernate as the JPA provider
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    // Set the entity manager factory interface explicitly
    em.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);

    // Set Hibernate properties
    em.setJpaProperties(hibernateProperties());

    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"); // Update dialect as needed
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.format_sql", "true");
    return properties;
  }
}
