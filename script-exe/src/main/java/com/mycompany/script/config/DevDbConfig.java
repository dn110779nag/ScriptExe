/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Заполнение базы для дева при старте.
 * 
 * @author nova
 */
@Configuration("devDbConfig")
//@Profile("dev")
public class DevDbConfig {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("securityJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    
    
    @Autowired
    @Qualifier("appPwdEncoder")
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() throws SQLException{
        String user = "admin";
        String password = "admin";
        
        try{
            jdbcTemplate.execute("create table users(username varchar(32), password varchar(64), enabled boolean)");
            
            
            String pe = passwordEncoder.encode(password);
            logger.debug("pwd={}; pe={}", password, pe);
            jdbcTemplate.update("insert into users values(?,?,?)", user, pe, true);
            jdbcTemplate.execute("create table authorities(username varchar(32), authority varchar(32))");
            jdbcTemplate.update("insert into authorities values(?,?)", user, "APP");
        } catch(Exception ex){
        }
    }
}
