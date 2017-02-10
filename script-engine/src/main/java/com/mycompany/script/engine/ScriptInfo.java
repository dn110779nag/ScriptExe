/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.engine;

import javax.script.CompiledScript;

/**
 * Контейнер с информацией о скрипте.
 * 
 * @author dn110
 */
@lombok.AllArgsConstructor
@lombok.Data
public class ScriptInfo {
    /**
     * Дата моодификации файла на диске.
     */
    private long tlm;
    /**
     * Скомпилированный файл.
     */
    private CompiledScript compiled;
}
