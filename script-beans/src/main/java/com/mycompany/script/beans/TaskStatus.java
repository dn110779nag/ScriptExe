/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.beans;

import java.util.Date;

/**
 * Текущий статус задачи.
 * 
 * @author nova
 */
@lombok.Data
public class TaskStatus {

    /**
     * Конструктор.
     * 
     * @param taskId 
     */
    public TaskStatus(long taskId) {
        this.taskId = taskId;
    }
    
    
    
    /**
     * Идентификатор задачи.
     */
    private long taskId;
    /**
     * Последняя дата старта.
     */
    private Date lastStart;
    /**
     * Последняя дата завершения.
     */
    private Date lastFinish;
    /**
     * Следующая дата запуска.
     */
    private Date nextStart;
    /**
     * Последний текст ошибки.
     */
    private String lastError;
    /**
     * Признак запуска.
     */
    private boolean running;
    
}
