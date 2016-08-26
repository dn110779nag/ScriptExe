/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.components;

import com.mycompany.script.beans.ConfigManager;
import com.mycompany.script.beans.Task;
import com.mycompany.script.beans.TaskStatus;
import com.mycompany.script.engine.ScriptExecutor;
import com.mycompany.script.engine.ScriptResult;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;

/**
 * Выполняемая задача для постановки в очередь
 * 
 * @author user
 */
public class RunnableTask implements Runnable {

    private final Task task;
    private final TaskStatus taskStatus;
    private final Logger logger;
    private final ScriptExecutor scriptExecutor;
    private final String basePath;
    private final ConfigManager configManager;

    public RunnableTask(Task task, TaskStatus taskStatus, 
            ScriptExecutor scriptExecutor, 
            ConfigManager configManager,
            String basePath) {
        this.task = task;
        this.taskStatus = taskStatus;
        this.logger = LoggerFactory.getLogger(task.getLoggerName());
        this.scriptExecutor = scriptExecutor;
        this.basePath = basePath;
        this.configManager = configManager;
    }
    
    private Map<String, Object> prepareBinding(){
        Map<String, Object> binding = new HashMap<>();
        binding.put("conf", configManager.getConfigTree());
        return binding;
    }

    @Override
    public void run() {
        String err = null;
        try {
            logger.trace("starting task {}, path {}", task.getId(), task.getPath());
            Map<String, Object> binding = prepareBinding();
            
            ScriptResult result = scriptExecutor.execScript(task.getPath(), basePath, logger, binding);
            if (result.getException() != null) {
                taskStatus.setLastError(""+result.getException());
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            err = ""+ex;
            
        } finally{
            synchronized(taskStatus){
                taskStatus.setLastFinish(new Date());
                taskStatus.setNextStart(new CronSequenceGenerator(task.getScheduler())
                        .next(taskStatus.getLastFinish()));
                logger.trace("nextStart={}", taskStatus.getNextStart());
                taskStatus.setRunning(false);
            }
        }
    }
}
