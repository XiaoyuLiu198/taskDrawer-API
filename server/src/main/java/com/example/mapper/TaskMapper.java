package com.example.service.mapper;


import com.example.service.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM tasks where name LIKE #{name}")
    List<Task> getTaskContainsName(@Param("name") String name);

    @Select("SELECT * FROM tasks where task_content LIKE #{description}")
    List<Task> getTaskContainsDescription(@Param("description") String description);

    @Select("SELECT * FROM tasks where time < #{higher}")
    List<Task> getTaskWithinInterval(@Param("higher") Number time);

}
