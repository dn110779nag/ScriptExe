package com.mycompany.script.dto;

/**
 * Базовый дто для ответов контроллера.
 * 
 * @author user
 */
@lombok.Data
@lombok.AllArgsConstructor
public class AnswerDto {
    /**
     * статус.
     */
    private boolean ok;
    /**
     * Сообщение.
     */
    private String msg;
    
    
}
