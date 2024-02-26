package com.services;

import com.model.TaskDTO;
import com.store.TaskStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskStore taskStore;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        // TODO filter data
        TaskDTO newTask = taskStore.save(taskDTO); // In-memory task store
        return new TaskDTO(newTask.getId(), newTask.getTitle(), newTask.getDescription(), newTask.isCompleted(),
                newTask.getExpiringDate());
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> listAllTasks() {
        return taskStore.findAll().stream().map(TaskDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
        if (taskStore.getTaskById(id) == null) {
            return null;
        }

        taskStore.setTitle(taskDTO.getTitle());
        taskStore.setDescription(taskDTO.getDescription());
        taskStore.setIsCompleted(taskDTO.isCompleted());
        return taskStore.save(taskDTO);
    }

    // TODO
    public void deleteTask(String id) {
    }

    public String getTaskId(String id) {
        return taskStore.getTaskId(id);
    }


    // TODO: get, set, update, delete...
}
