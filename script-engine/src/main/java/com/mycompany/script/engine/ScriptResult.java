/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine;

import lombok.Data;

/**
 * Класс для возврата результата отработки скрипта.
 * 
 * @author user
 */
@Data
public class ScriptResult {
    /**
     * Исключение.
     */
    private Throwable exception;
    
    /**
     * Результат отработки.
     */
    private Object result;
    
    /**
     * Дата начала выполнения скрипта.
     */
    private long start;
    
    /**
     * Дата окончания выполнения скрипта.
     */
    private long finish;
}
