/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine;

import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author user
 */
public interface ScriptExecutor {
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
            Map<String, Object> binding);
}
