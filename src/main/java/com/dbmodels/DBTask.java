package com.dbmodels;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class DBTask {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean isComplete;

    private Date expiringDate;

    public DBTask() {
    }

    // Getters et Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isTaskComplete() {
        return isComplete;
    }

    public void setIsTaskComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    // For debugging
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isComplete=" + isComplete +
                ", expiringDate=" + expiringDate +
                '}';
    }
}
