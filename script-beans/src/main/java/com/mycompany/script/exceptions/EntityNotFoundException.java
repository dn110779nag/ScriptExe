package com.mycompany.script.exceptions;

/**
 * Исключения в случае обращения к несуществующей сущности.
 * @author nova
 */
public class EntityNotFoundException extends RuntimeException{

    /**
     * Констркутор.
     * @param message сообщение
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
    
}
