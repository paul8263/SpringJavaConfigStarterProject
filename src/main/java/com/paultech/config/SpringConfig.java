package com.paultech.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by paulzhang on 11/07/2016.
 */
@Configuration
@ComponentScan(basePackages = "com.paultech.core.services")
@EnableJpaRepositories(basePackages = "com.paultech.core.repos")
//@ImportResource is for importing java config class, @PropertySource is for importing properties file
@PropertySource(value = "classpath:appConfig.properties")
@EnableTransactionManagement
public class SpringConfig {

//    Holds the key value pair stored in appConfig.properties
    @Autowired
    private Environment environment;

//    Config Apache Commons DBCP2
    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(environment.getProperty("mysql.username"));
        basicDataSource.setPassword(environment.getProperty("mysql.password"));
        basicDataSource.setUrl(environment.getProperty("mysql.url"));
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return basicDataSource;
    }

//    Config Entity Manager Factory
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties JpaProperties = new Properties();
        JpaProperties.setProperty("hibernate.show_sql", "true");
        JpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        JpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        localContainerEntityManagerFactoryBean.setJpaProperties(JpaProperties);
//        Set where entity classes locate
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.paultech.core.entities");

        localContainerEntityManagerFactoryBean.setDataSource(basicDataSource());
        return localContainerEntityManagerFactoryBean;
    }

//    Set JPA Transaction Manager
    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
