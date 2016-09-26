package com.mycompany.script;

import com.mycompany.script.engine.classloader.ClassLoaderHelper;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScriptExeApplication {
    
    private static final Logger LOG = LoggerFactory.getLogger(ScriptExeApplication.class);

    public static void main(String[] args) throws Exception {
//        addClassPath();
        SpringApplication.run(ScriptExeApplication.class, args);
    }
    
    
    private static void addClassPath() throws Exception{
        String cp = System.getProperty("custom-cp");
        LOG.info("custom-cp="+cp);
        if(cp!=null){
            ClassLoaderHelper.addFoldersWithJars(cp);
        }
    }
}
