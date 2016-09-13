package com.mycompany.script.controllers;

import com.mycompany.script.beans.Task;
import com.mycompany.script.components.TaskComponent;
import com.mycompany.script.dto.AnswerDto;
import com.mycompany.script.dto.StatusDto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с задачами.
 * 
 * @author user
 */
@RestController
@RequestMapping("task")
public class TaskController {
    
    
    private final TaskComponent taskComponent;

    @Autowired
    public TaskController(TaskComponent taskComponent) {
        this.taskComponent = taskComponent;
    }
    
    
    
    /**
     * Выгрузить список задач.
     * 
     * @return список задач
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Task> list(){
        return taskComponent.list();
    }
    
    /**
     * Добавить задачу.
     * 
     * @param t задача
     * @return задача после сохранения
     */
    @RequestMapping(method = RequestMethod.POST)
    public Task addTask(@RequestBody Task t){
        t = taskComponent.add(t);
        return t;
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public Task setTask(@RequestBody Task t){
        t = taskComponent.set(t);
        return t;
    }
    
    /**
     * Приостановаить/Включить задачу.
     * 
     * @param taskId ид задачи
     * @param statusDto статус
     * @return ответ
     */
    @RequestMapping(value = "/status/{taskId}", method = RequestMethod.POST)
    public AnswerDto setStatus(
            @PathVariable("taskId") int taskId,
            @RequestBody StatusDto statusDto
    ){
        
        return new AnswerDto(taskComponent.setEnabled(taskId, statusDto.isStatus()), null);
    }
    
    /**
     * Запуск задачи.
     * 
     * @param taskId ид задачи
     * @return ответ
     */
    @RequestMapping(value = "/run/{taskId}", method = RequestMethod.GET)
    public AnswerDto runTask(
            @PathVariable("taskId") int taskId
    ){
        return new AnswerDto(taskComponent.runTask(taskId), null);
    }
}
