package com.example.service.model;

import jakarta.persistence.*;

@Entity
@Table(name="task")
public class Task {
    public Task(Long id, String name, String task_content, Number time) {
        this.id = id;
        this.name = name;
        this.task_content = task_content;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, name="default")
    private String task_content;

    @Column(nullable = true)
    private Number time;

    public Task() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public Number getTime() {
        return time;
    }

    public void setTime(Number time) {
        this.time = time;
    }

    public String toTString(Task task){
        String dictOfTask = String.format("task id: %d, task name: %s, task content: %s, time needs to complete task: %d", task.getId(), task.getName(), task.getTask_content(), task.getTime());
        return dictOfTask;
    }
}
