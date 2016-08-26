/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.beans;

import java.util.Map;
import java.util.Properties;

/**
 * Менеджер получения конфига.
 * 
 * @author nova
 */
public interface ConfigManager {
    /**
     * Получить конфиг в линейном виде.
     * 
     * @return конфиг в линейном виде
     */
    Properties getPlainfConfig();
    
    /**
     * Получить конфиг в виде дерева.
     * 
     * @return конфиг в виде дерева
     */
    Map<String, Object> getConfigTree();
}
