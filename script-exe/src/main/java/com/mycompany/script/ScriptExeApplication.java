package com.mycompany.script;

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
        addClassPath();
        SpringApplication.run(ScriptExeApplication.class, args);
    }
    
    
    private static void addClassPath() throws Exception{
        String cp = System.getProperty("custom-cp");
        LOG.info("custom-cp="+cp);
        if(cp!=null){
            String[] paths = cp.split(":");
            
            URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class sysclass = URLClassLoader.class;
            
            Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            for(String p : paths){
                if(p.endsWith("*")){
                    p = p.substring(0, p.length()-1);
                    File[] dir = new File(p).listFiles();
                    for(File f : dir){
                        String ap = f.getAbsolutePath();
                        LOG.info("add "+ap);
                        method.invoke(sysloader, new Object[]{new URL("file:///"+ap)});
                    }
                } else {
                    String ap = new File(p).getAbsolutePath();
                    LOG.info("add "+ap);
                    method.invoke(sysloader, new Object[]{new URL("file:///"+ap+File.separator)});
                }
            }
            
            
            
        }
    }
}
