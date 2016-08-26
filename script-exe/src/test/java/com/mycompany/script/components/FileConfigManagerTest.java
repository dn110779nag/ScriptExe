/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.components;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author nova
 */
public class FileConfigManagerTest {
    
    
    
    public FileConfigManagerTest() {
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
     * Test of init method, of class FileConfigManager.
     */
    @Test
    @Ignore
    public void testInit() throws Exception {
        System.out.println("init");
        FileConfigManager instance = null;
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalize method, of class FileConfigManager.
     */
    @Test
    @Ignore
    public void testFinalize() {
        System.out.println("finalize");
        FileConfigManager instance = null;
        instance.finalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlainfConfig method, of class FileConfigManager.
     */
    @Test
    public void testGetPlainfConfig() throws IOException {
        System.out.println("getPlainfConfig");
        FileConfigManager instance = new FileConfigManager("config/config.properties");
        instance.init();
        Properties result = instance.getPlainfConfig();
        assertEquals("Проверка свойства", result.get("obj.test.string"));
        instance.finalize();

    }

    /**
     * Test of getConfigTree method, of class FileConfigManager.
     */
    @Test
    public void testGetConfigTree() throws IOException {
        System.out.println("getConfigTree");
        FileConfigManager instance = new FileConfigManager("config/config.properties");
        instance.init();
        Map<String, Object> result = instance.getConfigTree();
        assertEquals("Проверка свойства", ((Map<String, Object>)((Map<String, Object>)result.get("obj")).get("test")).get("string"));
        instance.finalize();
    }
    
}
