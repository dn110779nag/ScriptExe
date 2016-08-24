
import com.mycompany.script.dto.AnswerDto;
import com.mycompany.script.dto.StatusDto;
import java.util.List;
import javafx.concurrent.Task;
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
    
    
    /**
     * Выгрузить список задач.
     * 
     * @return список задач
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Task> list(){
        return null;
    }
    
    /**
     * Добавить задачу.
     * 
     * @param t задача
     * @return задача после сохранения
     */
    @RequestMapping(method = RequestMethod.POST)
    public Task addTask(Task t){
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
        return new AnswerDto(true, null);
    }
}
