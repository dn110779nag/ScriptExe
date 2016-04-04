/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine.js.nashorn;

import com.mycompany.script.engine.ScriptResult;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.Logger;

/**
 *
 * @author user
 */
public class JsNashornScriptExecutor {
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
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();
        try{
            ScriptContext newContext = new SimpleScriptContext();
            newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
            Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
            engineScope.putAll(binding);
            engineScope.put("logger", logger);
            engineScope.put("currentDir", new File("").getAbsolutePath());
            result.setStart(System.currentTimeMillis());
            try(Reader reader = Files.newBufferedReader(new File(basePath, scriptPath).toPath())){
                Object value = engine.eval(reader, engineScope);
                result.setResult(value);
            }
        } catch(IOException|ScriptException|RuntimeException ex){
            logger.error("", ex);
            result.setException(ex);
        } finally{
            result.setFinish(System.currentTimeMillis());
        }
        return result;
    }
}
