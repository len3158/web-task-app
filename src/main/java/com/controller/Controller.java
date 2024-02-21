package com.controller;

import com.datamodel.TaskDTO;
import com.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")
public class Controller {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<taskDTO>> listTasks() {
        return ResponseEntity.ok(taskService.listTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<taskDTO> getTaskId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<taskDTO> updateTask(@PathVariable Long id, @RequestBody taskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
