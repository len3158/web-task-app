package com.interfaces;

import java.util.UUID;

public interface ITask {
    UUID getId();
    void setId(Long id); // TODO

    String getTitle();
    void setTitle(String title);

    String getDescription();
    void setDescription(String description);

    boolean isCompleted();
    void setIsCompleted(boolean isCompleted);

    String getExpiringDate();
    void setExpiringDate(String expiringDate);

    Boolean deleteTask();

    String getTaskId(String id);
}
