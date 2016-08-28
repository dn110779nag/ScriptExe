/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.config;

import com.mycompany.script.engine.UniversalScriptExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Главная конфигурация.
 * 
 * @author user
 */
@Configuration
//@EnableAsync
@EnableScheduling
public class MainConfig {
    
    @Bean
    public UniversalScriptExecutor prepareScriptExecutor(){
        UniversalScriptExecutor executor = new UniversalScriptExecutor();
        return executor;
    }
    
    @Bean(name = "appTaskExecutor")
    public TaskExecutor prepareTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(10);
        return taskExecutor;
    }
    
    
    
}
