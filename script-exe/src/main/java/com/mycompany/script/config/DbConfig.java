/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.config;

import com.mycompany.script.beans.Task;
import com.mycompany.script.repository.JdbcTaskRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTaskRepository jdbcTaskRepository;
    
    @Value("${create.script.4.tasks}")
    private String createScript;
    
    @PostConstruct
    public void init() throws SQLException{
        boolean tableExists = false;
        logger.trace("db-init");
        try(Connection conn  = jdbcTemplate.getDataSource().getConnection(); 
                ResultSet rs = conn.getMetaData().getTables(null, null, "TASKS", null);){
            if(rs.next()){
                tableExists = true;
            }
        }
        logger.trace("tableExists={}", tableExists);
        if(!tableExists){
            jdbcTemplate.execute(createScript);
            Task t = Task.builder()
                    .id(0)
                    .path("test.groovy")
                    .enabled(true)
                    .loggerName("test")
                    .scheduler("0 *\\1 * * * *")
                    .description("Тестовый скрипт")
                    .build();
            jdbcTaskRepository.addTask(t);
        }
        
    }
}
