/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.components;

import com.mycompany.script.beans.ConfigManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Менеджер конфигурации.
 * 
 * @author nova
 */
@Component
public class FileConfigManager implements ConfigManager{
    
    private long lastupdate = 0l;
    private Properties plainfConfig;
    private Map<String, Object> configTree;
    
    private final String filePath;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FileConfigManager(@Value("${config.path}") String filePath) {
        this.filePath = filePath;
    }
    
    private void reload() throws IOException{
        File f = new File(filePath);
        
//        logger.debug("{} f.lastModified()={}>lastupdate={} ==> {}", 
//                f.getAbsolutePath(), f.lastModified(), lastupdate, f.lastModified()>lastupdate);
        if(f.lastModified()>lastupdate){
            Properties p = new Properties();
            try(BufferedReader br = Files.newBufferedReader(f.toPath(), Charset.forName("utf-8"))){
                p.load(br);
                plainfConfig = p;
                configTree = convertPlainToTree(p);
                lastupdate = f.lastModified();
//                logger.debug("p={}", p);
            }
        }
    }
    
    
    private Thread fileWatchDog = new Thread("configWatchDog"){
        @Override
        public void run() {
            try{
                do{
                    try {
                        reload();
                        sleep(2000);
                    } catch (IOException ex) {
                        logger.error("", ex);
                    }
                } while(!isInterrupted());
            } catch(InterruptedException ex){
                logger.info(Thread.currentThread().getName()+" stopping");
            }
        }
        
    };
    
    @PostConstruct
    public void init() throws IOException{
        reload();
        fileWatchDog.start();
    }
    @PreDestroy
    public void finalize(){
        fileWatchDog.interrupt();
    }
    
    
    private Map<String, Object> convertPlainToTree(Properties p){
        Map<String, Object> map = new HashMap<>();
        p.entrySet().forEach((e) -> {
            String[] keys = e.getKey().toString().split("[.]");
            Map<String, Object> tmp = map;
            for(int i=0; i<keys.length; i++){
                String k = keys[i];
                Object o = tmp.get(k);
                if((o==null || !(o instanceof Map)) && i<keys.length-1 ){
                    Map<String, Object> newMap = new HashMap<>();
                    tmp.put(k, newMap);
                    tmp = newMap;
                } else if(o instanceof Map){
                    tmp = (Map<String, Object>) o;
                } else if(i==keys.length-1){
                    tmp.put(k, e.getValue());
                }
            }
        });
        return map;
    }
    
    

    @Override
    public Properties getPlainfConfig() {
        return plainfConfig;
    }

    @Override
    public Map<String, Object> getConfigTree() {
        return configTree;
    }
    
}
