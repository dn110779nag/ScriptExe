/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Конфигурация базы. если не существует.
 * @author nova
 */
@Configuration
public class DbConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Value("${create.script.4.task}")
    private String createScript;
    
    @PostConstruct
    public void init() throws SQLException{
        boolean tableExists = false;
        try(Connection conn  = jdbcTemplate.getDataSource().getConnection(); 
                ResultSet rs = conn.getMetaData().getTables(null, null, "TASK", null);){
            if(rs.next()){
                tableExists = true;
            }
        }
        if(!tableExists){
            jdbcTemplate.execute(createScript);
        }
    }
}
