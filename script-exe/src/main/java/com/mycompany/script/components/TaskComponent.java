package com.mycompany.script.components;

import com.mycompany.script.beans.ConfigManager;
import com.mycompany.script.beans.Task;
import com.mycompany.script.beans.TaskStatus;
import com.mycompany.script.dao.TaskRepository;
import com.mycompany.script.engine.ScriptExecutor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

/**
 * Основной компонент, управляющий выполнением задач.
 * 
 * @author user
 */
@Component
@DependsOn("dbConfig")
public class TaskComponent {

    private final TaskExecutor executor;
    private final TaskRepository taskRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Task> taskList = new ArrayList<>();
    private final Map<Long, TaskStatus> tasksStatuses = new HashMap<>();
    private final ScriptExecutor scriptExecutor;
    private final String basePath;
    private final ConfigManager configManager;

    @Autowired
    public TaskComponent(
            TaskExecutor executor,
            TaskRepository taskRepository,
            ScriptExecutor scriptExecutor,
            ConfigManager configManager,
            @Value("${scripts.base-path}") String basePath) {
        this.executor = executor;
        this.taskRepository = taskRepository;
        this.scriptExecutor = scriptExecutor;
        this.basePath = basePath;
        this.configManager = configManager;
    }

    @PostConstruct
    public void init() {
        taskList.addAll(taskRepository.list());
        taskList.forEach(t -> {
            TaskStatus taskStatus = new TaskStatus(t.getId());
            tasksStatuses.put(t.getId(), taskStatus);
            taskStatus.setNextStart(new CronSequenceGenerator(t.getScheduler()).next(new Date()));
        });
    }

    @Scheduled(fixedDelay = 10000l)
    public void runTasks() {
        
        for (Task t : taskList) {
            TaskStatus taskStatus = tasksStatuses.get(t.getId());
            logger.trace("runTasks: task={}, nextStart={}, isEnabled={}, isRunning={}, {}", 
                    t.getId(), taskStatus.getNextStart(), t.isEnabled(), taskStatus.isRunning(), 
                    taskStatus.getNextStart().before(new Date()));
            if (t.isEnabled() && !taskStatus.isRunning() && taskStatus.getNextStart().before(new Date())) {
                logger.trace("sending task={}, nextStart={}, isEnabled={}, isRunning={}", 
                    t.getId(), taskStatus.getNextStart(), t.isEnabled(), taskStatus.isRunning());
                synchronized(taskStatus){
                    taskStatus.setLastError(null);
                    taskStatus.setLastFinish(null);
                    taskStatus.setLastStart(new Date());
                    taskStatus.setNextStart(null);
                    taskStatus.setRunning(true);
                }
                executor.execute(new RunnableTask(t, taskStatus, scriptExecutor, configManager, basePath));
            }
        }
    }

}
