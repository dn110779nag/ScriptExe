package com.example;

import com.mycompany.script.engine.ScriptResult;
import com.mycompany.script.engine.UniversalScriptExecutor;
import com.mycompany.script.engine.classloader.ClassLoaderHelper;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScriptEnginePoligonApplication {

    public static void main(String[] args) throws Exception {
        
//        org.springframework.boot.loader.Launcher l;
        SpringApplication.run(ScriptEnginePoligonApplication.class, args);
        
        UniversalScriptExecutor scriptExecutor = new UniversalScriptExecutor();
        ClassLoaderHelper.addFoldersWithJars("/home/data/TMP/4/go-reglament/lib/*");

        ScriptResult res = scriptExecutor.execScript("test.groovy",
                "/home/data/TMP/4/go-reglament/scripts",
                LoggerFactory.getLogger("test"), new HashMap<>());

    }
}
