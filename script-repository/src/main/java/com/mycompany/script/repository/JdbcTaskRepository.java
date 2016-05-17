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
import org.springframework.transaction.annotation.Transactional;

/**
 * Репозиторий для работы с задачами.
 * 
 * @author nova
 */
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
                + "task_logger_name "
                + "from tasks", (ResultSet rs, int i) -> {
                    Task t = new Task();
                    t.setId(rs.getLong("task_id"));
                    t.setEnabled(rs.getBoolean("task_enabled"));
                    t.setScheduler(rs.getString("task_scheduler"));
                    t.setLoggerName(rs.getString("task_logger_name"));
                    return t;
        });
    }

    @Override
    @Transactional
    public Task addTask(Task t) {
        t.setId(generateId());
        jdbcTemplate.update("insert into task(task_id, "
                + "task_path, "
                + "task_enabled, "
                + "task_scheduler, "
                + "task_logger_name) values(?,?,?,?,?)", 
                t.getId(), t.getPath(), t.isEnabled(), t.getScheduler(), t.getLoggerName());
        return t;
    }

    @Override
    public Task setTask(Task t) {
        int res = jdbcTemplate.update("update task set task_path=?,"
                + "task_enabled=?, task_enabled=?, task_scheduler = ?,"
                + "task_logger_name=? where task_id=?",
                t.getPath(), t.isEnabled(), t.getScheduler(), t.getLoggerName(), t.getId());
        if(res==0) throw new EntityNotFoundException("Задача с id"+t.getId()+" не найдена в базе");
        return t;
    }
    
    /**
     * Генерация счетчика.
     * 
     * @return новый счетчик
     */
    private long generateId(){
        Long v = jdbcTemplate.queryForObject("select max(task_id) from task", Long.class);
        if(v==null){
            v = 1l;
        }
        return v+1;
    }
    
    
    
}
