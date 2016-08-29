package com.mycompany.script.components;

import com.mycompany.script.beans.ConfigManager;
import com.mycompany.script.beans.Task;
import com.mycompany.script.beans.TaskStatus;
import com.mycompany.script.dao.TaskRepository;
import com.mycompany.script.dto.TaskInfoDto;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final Map<Long, Task> taskMap = new HashMap<>();
    private final Map<Long, TaskStatus> tasksStatuses = new HashMap<>();
    private final ScriptExecutor scriptExecutor;
    private final String basePath;
    private final ConfigManager configManager;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TaskComponent(
            @Qualifier("appTaskExecutor") TaskExecutor executor,
            TaskRepository taskRepository,
            ScriptExecutor scriptExecutor,
            ConfigManager configManager,
            SimpMessagingTemplate messagingTemplate,
            @Value("${scripts.base-path}") String basePath) {
        this.executor = executor;
        this.taskRepository = taskRepository;
        this.scriptExecutor = scriptExecutor;
        this.basePath = basePath;
        this.configManager = configManager;
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() {
        taskList.addAll(taskRepository.list());
        taskList.forEach(this::prepareTaskStatus);
    }

    private void prepareTaskStatus(Task t) {
        TaskStatus taskStatus = new TaskStatus(t.getId());
        tasksStatuses.put(t.getId(), taskStatus);
        taskStatus.setNextStart(new CronSequenceGenerator(t.getScheduler()).next(new Date()));
        taskMap.put(t.getId(), t);
    }

    @Scheduled(fixedDelay = 5000l)
    public void runTasks() {

        for (Task t : taskList) {
            runTask(t);
        }
    }

    private boolean runTask(Task t) {
        TaskStatus taskStatus = tasksStatuses.get(t.getId());
//        logger.trace("runTasks: task={}, nextStart={}, isEnabled={}, isRunning={}, {}",
//                t.getId(), taskStatus.getNextStart(), t.isEnabled(), taskStatus.isRunning(),
//                taskStatus.getNextStart().before(new Date()));

        try {
            if (t.isEnabled() && !taskStatus.isRunning() && taskStatus.getNextStart().before(new Date())) {
                logger.trace("sending task={}, nextStart={}, isEnabled={}, isRunning={}",
                        t.getId(), taskStatus.getNextStart(), t.isEnabled(), taskStatus.isRunning());
                synchronized (taskStatus) {
                    taskStatus.setLastError(null);
                    taskStatus.setLastFinish(null);
                    taskStatus.setLastStart(new Date());
                    taskStatus.setNextStart(null);
                    taskStatus.setRunning(true);
                }
                executor.execute(new RunnableTask(t, taskStatus, scriptExecutor, configManager, basePath));
                return true;
            } else {
                return false;
            }
        } finally {
            TaskInfoDto taskInfoDto = TaskInfoDto.builder()
                    .id(t.getId())
                    .running(taskStatus.isRunning())
                    .enabled(t.isEnabled())
                    .lastFinish(taskStatus.getLastFinish())
                    .lastStart(taskStatus.getLastStart())
                    .nextStart(taskStatus.getNextStart())
                    .lastError(taskStatus.getLastError())
                    .build();
            messagingTemplate.convertAndSend("/queue/statuses", taskInfoDto);
        }
    }

    /**
     * Получить список тасок.
     *
     * @return список тасок
     */
    public List<Task> list() {
        return taskList;
    }

    /**
     * Добавить таску.
     *
     * @param t таска
     * @return таска после добавления
     */
    public Task add(Task t) {
        t = taskRepository.addTask(t);
        taskList.add(t);
        prepareTaskStatus(t);
        return t;
    }

    /**
     * Включить/выключить таску.
     *
     * @param taskId код таски
     * @param status новый статус
     * @return признак - удалось/не удалось
     */
    public boolean setEnabled(long taskId, boolean status) {

        Task task = taskMap.get(taskId);
//        logger.debug("taskMap=>{}, taskId={}, task={}", taskMap, taskId, task );
        synchronized (task) {
            if (task.isEnabled() == status) {
                return false;
            } else {
                task.setEnabled(status);
                taskRepository.setTask(task);
                return true;
            }
        }
    }

    /**
     * Запуск таски.
     *
     * @param taskId
     * @return признак - удалось/не удалось
     */
    public boolean runTask(long taskId) {
        Task task = taskMap.get(taskId);
        return runTask(task);
    }

}
