package com.example.service.service;

import com.example.service.dao.TaskDao;
import com.example.service.mapper.TaskMapper;
import com.example.service.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskDao taskDao;
    private TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskDao taskDao, TaskMapper taskMapper){
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
    }

    public Task addTask(Task task) throws Exception {
        if(task.getName().isEmpty()){
            throw new Exception("Task name can not be empty");
        }
        return taskDao.save(task);
    }

    public Task updateTask(Task task) throws  Exception{
        if (task.getId() == null || taskDao.existsById((task.getId()))){
            throw new Exception("Cannot find this task in the database");
        }
        return taskDao.save(task);
    }

    public List<Task> getAllTasks(){
        return (List<Task>) taskDao.findAll();
    }


    public List<Task> matchTasksName(String name){
        return taskMapper.getTaskContainsName(name);
    }


    public List<Task> matchTasksDescribe(String description){
        return taskMapper.getTaskContainsDescription(description);
    }

    public List<Task> matchTasksTime(Number time){
        return taskMapper.getTaskWithinInterval(time);
    }

}
