package com.controller;

import com.model.TaskDTO;
import com.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskDTO>> listAllTasks() {
        return ResponseEntity.ok(taskService.listAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskId(@PathVariable String id) {
        return ResponseEntity.ok(taskService.getTaskId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
