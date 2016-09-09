/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Утилитарный кдасс для настройки класслоадера
 * @author nova
 */
public class ClassLoaderHelper {
    
    static private final Logger LOG = LoggerFactory.getLogger(ClassLoaderHelper.class);
    
    /**
     * Добавление каталога в путь классов.
     * 
     * @param path каталог
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws MalformedURLException 
     */
    public static final void addFolder(String path) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        String absolutePath = new File(path).getAbsolutePath();
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;
        Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(sysloader, new Object[]{new URL("file:///"+absolutePath+File.separator)});
    }
    
    
    
    /**
     * Добавляет каталоги в класспас. Каталоги разделяются символом : или ; или ,.
     * Если какой-то путь добавляется с *, то добавляются и все дочерние элементы 
     * первого уровня вложенности.
     * 
     * @param path пути
     * @throws Exception 
     */
    public static void addFoldersWithJars(String path) throws Exception{
        
            String[] paths = path.split("[:;,]");
            
            URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class sysclass = URLClassLoader.class;
            
            Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            for(String p : paths){
                if(p.endsWith("*")){
                    p = p.substring(0, p.length()-1);
                    method.invoke(sysloader, new Object[]{new URL("file:///"+p)});
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
