package com.mycompany.script.beans;

/**
 * Класс, описывающий задачу.
 * 
 * @author nova
 */
@lombok.Data
public class Task {
    /**
     * Код задачи.
     */
    private long id;
    
    /**
     * Имя логера.
     */
    private String loggerName;
    
    /**
     * Путь к скрипту.
     */
    private String path;
    /**
     * Расписание запуска.
     */
    private String scheduler;
    /**
     * Признак отключенности.
     */
    private boolean disabled;
    
}
