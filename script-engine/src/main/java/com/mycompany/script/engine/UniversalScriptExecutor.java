package com.mycompany.script.engine;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.slf4j.Logger;

/**
 *
 * @author user
 */
public class UniversalScriptExecutor {
    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    
    private String getExtension(String fileName){
        int pos = fileName.lastIndexOf('.');
        if(pos!=-1){
            return fileName.substring(pos+1);
        } else {
            throw new IndexOutOfBoundsException("Невозможно определить расширение файла "+fileName);
        }
    }
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
        String extension = getExtension(scriptPath);
        ScriptEngine engine = scriptEngineManager.getEngineByExtension(extension);
        
        try{
            if(engine==null){
                throw new IOException("Движок не найден для расширения "+extension);
            }
            ScriptContext newContext = new SimpleScriptContext();
            newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
            Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
            engineScope.putAll(binding);
            engineScope.put("logger", logger);
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
