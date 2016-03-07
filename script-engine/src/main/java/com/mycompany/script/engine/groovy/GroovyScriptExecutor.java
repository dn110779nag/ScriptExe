/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine.groovy;

import com.mycompany.script.engine.ScriptResult;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;

/**
 * Запуск скриптов на груви.
 * 
 * @author user
 */
public class GroovyScriptExecutor{
    /**
     * Запуск скрипта.
     * 
     * @param scriptPath путь на диске к скрипту
     * @param basePath базовый путь
     * @param logger логгер
     * @param binding биндинг
     * @return результат
     */
    public ScriptResult execScript(
            String scriptPath, 
            String basePath,
            Logger logger,
            Map<String, Object> binding){
        ScriptResult result = new ScriptResult();
        
        try{
            GroovyScriptEngine engine = new GroovyScriptEngine(basePath);
            Binding b = new Binding(binding);
            b.setProperty("logger", logger);
            result.setStart(System.currentTimeMillis());
            Object value = engine.run(scriptPath, b);
            result.setResult(value);
        } catch(IOException|ResourceException|ScriptException ex){
            result.setException(ex);
        } finally{
            result.setFinish(System.currentTimeMillis());
        }
        return result;
    }
}
