package com.mycompany.script.beans;

/**
 * Класс, описывающий задачу.
 * 
 * @author nova
 */
@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
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
     * Признак включенности.
     */
    private boolean enabled;
    
    /**
     * Описание задачи.
     */
    private String description;
    
}
