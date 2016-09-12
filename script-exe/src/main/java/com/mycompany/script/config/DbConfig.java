/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Конфигурация компонентов для работы с базой данных.
 *
 * @author nova
 */
@Configuration
public class DbConfig {
    
    

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary
    @Autowired
    public JdbcTemplate configureJdbcTempalate(@Qualifier("dataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
