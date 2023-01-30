package api;
import com.example.service.dao.TaskDao;
import com.example.service.model.Task;
import com.example.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class ServerAppilcation {

    public static void main(String[] args) {
        SpringApplication.run(ServerAppilcation.class, args);
    }
    private TaskService taskService;
    private Task task;

    @Autowired
    public ServerAppilcation(TaskService taskService, Task task) {
        this.taskService = taskService;
        this.task = task;
    }

    @RequestMapping("/enterTask")
    @PostMapping
    public ResponseEntity<String> enterTask(@RequestBody Task task){
        try{
            Task savedTask = taskService.addTask(task);
            return ResponseEntity.ok("Successfully added task.");
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/getallTasks")
    public List<String> retrieveAllTasks(){
        List<String> res = taskService.getAllTasks().stream()
                .map(i -> task.toTString(i))
                .collect(Collectors.toList());
        return res;
    }


    @GetMapping("/matchTaskByName")
    public List<String> matchTasksByName(@RequestBody String name){
        List<String> res = taskService.matchTasksName(name).stream()
                .map(i -> task.toTString(i))
                .collect(Collectors.toList());
        return res;
    }

    @GetMapping("/matchTaskByDescription")
    public List<String> matchTaskByDescription(@RequestBody String description){
        List<String> res = taskService.matchTasksDescribe(description).stream()
                .map(i -> task.toTString(i))
                .collect(Collectors.toList());
        return res;
    }


    @GetMapping("/matchTasksByTime")
    public List<String> matchTasksTime(@RequestBody Number time){
        List<String> res = taskService.matchTasksTime(time).stream()
                .map(i -> task.toTString(i))
                .collect(Collectors.toList());
        return res;
    }

}
