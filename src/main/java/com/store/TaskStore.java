package com.store;

import com.model.TaskDTO;
import com.interfaces.ITask;

import java.util.UUID;
//import io.micrometer.core.instrument.binder.db.MetricsDSLContext;

public class TaskStore implements ITask {
    public TaskDTO findById(String id) {

        return null;
    }

    /**
     * @return
     */
    @Override
    public UUID getId() {
        return null;
    }

    /**
     * @param id
     */
    @Override
    public void setId(Long id) {

    }

    /**
     * @return
     */
    @Override
    public String getTitle() {
        return null;
    }

    /**
     * @param title
     */
    @Override
    public void setTitle(String title) {

    }

    /**
     * @return
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * @param description
     */
    @Override
    public void setDescription(String description) {

    }

    /**
     * @return
     */
    @Override
    public boolean isCompleted() {
        return false;
    }

    /**
     * @param isCompleted
     */
    @Override
    public void setIsCompleted(boolean isCompleted) {

    }

    /**
     * @param isCompleted
     */
    @Override
    public void setIsCompleted(Boolean isCompleted) {

    }

    /**
     * @return
     */
    @Override
    public String getExpiringDate() {
        return null;
    }

    /**
     * @param expiringDate
     */
    @Override
    public void setExpiringDate(String expiringDate) {

    }

    /**
     * @return
     */
    @Override
    public Boolean deleteTask() {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public String getTaskId(String id) {
        return null;
    }

    /**
     *
     * @param id
     * @return
     */

    public TaskDTO getTaskById(String id) {
        return null;
    }

    public TaskDTO save(TaskDTO task) {
        return null;
    }
}
