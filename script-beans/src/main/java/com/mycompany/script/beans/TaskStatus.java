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
public class TaskStatus {
    /**
     * Идентификатор задачи.
     */
    public long taskId;
    /**
     * Последняя дата старта.
     */
    public Date lastStart;
    /**
     * Последняя дата завершения.
     */
    public Date lastFinish;
    /**
     * Следующая дата запуска.
     */
    public Date nextStart;
    /**
     * Последний текст ошибок.
     */
    public String lastError;
    /**
     * Признак запуска.
     */
    private boolean running;
}
