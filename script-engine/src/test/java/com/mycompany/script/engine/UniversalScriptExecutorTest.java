/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dn110
 */
public class UniversalScriptExecutorTest {
    
    public UniversalScriptExecutorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execScript method, of class UniversalScriptExecutor.
     */
    @Test
    public void testExecScript() {
        System.out.println("execScript");
        String scriptPath = "SimpleGroovyScript.groovy";
        String basePath = "src\\test\\groovy\\".replaceAll("\\\\", File.separator);
        Logger logger = LoggerFactory.getLogger(UniversalScriptExecutorTest.class);
        Map<String, Object> binding = new HashMap<>();
        UniversalScriptExecutor instance = new UniversalScriptExecutor();
        while(true){
            ScriptResult result = instance.execScript(scriptPath, basePath, logger, binding);
            try {
                Thread.sleep(1000);
                //select filter(heap.classes(), "/Script/.test(it.name)")
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(UniversalScriptExecutorTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
//        assertEquals("hello", result.getResult());
        
    }
    
}
