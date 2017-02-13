package com.mycompany.script.engine;

import com.mycompany.script.engine.classloader.ClassLoaderHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Универсальный запускатель скриптов.
 *
 * @author user
 */
public class UniversalScriptExecutor implements ScriptExecutor {

    private static final Set<String> CLASSPATH = new HashSet<>();
    
    Map<String, ScriptInfo> map = new HashMap<>();

    /**
     * Добавление корневой папки со скриптами в класспас, чтобы работыли либы.
     * 
     * @param path путь к корневой папке
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws MalformedURLException 
     */
    private static final void addPath(String path) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        String absolutePath = new File(path).getAbsolutePath();
        System.out.println(absolutePath);
        if(!CLASSPATH.contains(absolutePath)){
            ClassLoaderHelper.addFolder(path);
            CLASSPATH.add(absolutePath);
        }
        
    }

    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

    /**
     * Определение расширения файла.
     *
     * @param fileName имя файла
     * @return расширение
     */
    private String getExtension(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos != -1) {
            return fileName.substring(pos + 1);
        } else {
            throw new IndexOutOfBoundsException("Невозможно определить расширение файла " + fileName);
        }
    }
    
    private String readScript(String basePath, String scriptPath) throws IOException{
        StringBuilder b = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(new File(basePath, scriptPath).toPath())) {
                
                String line;
                while((line = reader.readLine())!=null){
                    b.append(line).append('\n');
                }
        }
        return b.toString();
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
    @Override
    public ScriptResult execScript(
            String scriptPath,
            String basePath,
            Logger logger,
            Map<String, Object> binding) {

        ScriptResult result = new ScriptResult();

        try {
//            addPath(basePath);
            String extension = getExtension(scriptPath);
            ScriptEngine engine = scriptEngineManager.getEngineByExtension(extension);
            
            if (engine == null) {
                throw new IOException("Движок не найден для расширения " + extension);
            }
            ScriptContext newContext = new SimpleScriptContext();
            newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
            Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
            engineScope.putAll(binding);
            engineScope.put("logger", logger);
            result.setStart(System.currentTimeMillis());
            if(engine instanceof Compilable){
                File f = new File(basePath, scriptPath); 
                ScriptInfo info = map.get(f.getAbsolutePath());
                
                if(info==null || f.lastModified()>info.getTlm()){
                    Compilable compEngine = (Compilable) engine;
                    CompiledScript compiledScript = compEngine.compile(readScript(basePath, scriptPath));
                    info = new ScriptInfo(f.lastModified(), compiledScript);
                    map.put(f.getAbsolutePath(), info);
                    
                }
                Object value = info.getCompiled().eval(engineScope);
                result.setResult(value);
            } else {
            
                Object value = engine.eval(readScript(basePath, scriptPath), engineScope);
                result.setResult(value);
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            result.setException(ex);
        } finally {
            result.setFinish(System.currentTimeMillis());
        }
        return result;
    }

//    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
//
//        UniversalScriptExecutor scriptExecutor = new UniversalScriptExecutor();
//
//        ScriptResult res = scriptExecutor.execScript("test.groovy",
//                "E:\\Projects\\ScriptExe\\script-exe\\scripts",
//                LoggerFactory.getLogger("test"), new HashMap<>());
//
//    }

}
