/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.repository;

import com.mycompany.script.beans.Task;
import com.mycompany.script.dao.TaskRepository;
import com.mycompany.script.exceptions.EntityNotFoundException;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Репозиторий для работы с задачами.
 * 
 * @author nova
 */
@Component
public class JdbcTaskRepository implements TaskRepository{
    private final JdbcTemplate jdbcTemplate;
    

    /**
     * Конструктор.
     * @param jdbcTemplate темплей для работы с базой данных.
     */
    @Autowired
    public JdbcTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Task> list() {
        return jdbcTemplate.query("select task_id, "
                + "task_path, "
                + "task_enabled, "
                + "task_scheduler, "
                + "task_logger_name, "
                + "task_description "
                + "from tasks", (ResultSet rs, int i) -> {
                    Task t = Task.builder()
                            .id(rs.getLong("task_id"))
                            .enabled(rs.getBoolean("task_enabled"))
                            .scheduler(rs.getString("task_scheduler"))
                            .loggerName(rs.getString("task_logger_name")) 
                            .path(rs.getString("task_path"))
                            .description(rs.getString("task_description"))
                            .build();
                    return t;
        });
    }

    @Override
    @Transactional
    public Task addTask(Task t) {
        t.setId(generateId());
        jdbcTemplate.update("insert into tasks(task_id, "
                + "task_path, "
                + "task_enabled, "
                + "task_scheduler, "
                + "task_logger_name, task_description) values(?,?,?,?,?, ?)", 
                t.getId(), t.getPath(), t.isEnabled(), t.getScheduler(), t.getLoggerName(), t.getDescription());
        return t;
    }

    @Override
    public Task setTask(Task t) {
        int res = jdbcTemplate.update("update tasks set task_path=?,"
                + "task_enabled=?, task_scheduler = ?,"
                + "task_logger_name=?, task_description=? where task_id=?",
                t.getPath(), t.isEnabled(), t.getScheduler(), t.getLoggerName(), t.getDescription(), t.getId());
        if(res==0) throw new EntityNotFoundException("Задача с id"+t.getId()+" не найдена в базе");
        return t;
    }
    
    /**
     * Генерация счетчика.
     * 
     * @return новый счетчик
     */
    private long generateId(){
        Long v = jdbcTemplate.queryForObject("select max(task_id) from tasks", Long.class);
        if(v==null){
            v = 1l;
        }
        return v+1;
    }
    
    
    
}
