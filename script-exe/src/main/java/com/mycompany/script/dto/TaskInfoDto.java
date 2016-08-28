/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.dto;

import java.util.Date;

/**
 * Информация о задачах.
 * 
 * @author Alex
 */
@lombok.Getter
@lombok.Builder
public class TaskInfoDto {
    private final long id;
    private final boolean running;
    private final boolean enabled;
    private final Date lastStart;
    private final Date lastFinish;
    private final Date nextStart;
    private final String lastError;
}
