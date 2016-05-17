/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.dao;

import com.mycompany.script.beans.Task;
import java.util.List;

/**
 * Интерфейс необходимый для работы с задачами.
 * 
 * @author nova
 */
public interface TaskDao {
    /**
     * Изменение признака выполнения задачи
     * @param t задача
     * @param isRunning признак
     */
    void setRunning(Task t, boolean isRunning);
    /**
     * Получить список задач.
     * 
     * @return список задач
     */
    List<Task> list();
    /**
     * Добавить задачу.
     * 
     * @param t задача
     * @return задача
     */
    Task addTask(Task t);
    
    /**
     * Изменить задачу.
     * 
     * @param t задача
     * @return задача
     */
    Task setTask(Task t);
}
