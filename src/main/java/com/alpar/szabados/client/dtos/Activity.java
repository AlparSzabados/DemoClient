package com.alpar.szabados.client.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Activity implements Serializable {
    private long activityId, userId;
    private String activityName, activityDate;
    private TaskStatus taskStatus;

    public Activity() {
    }

    public Activity(String activityName, TaskStatus taskStatus) {
        this.activityName = activityName;
        this.taskStatus = taskStatus;
    }

    public Activity(Long userId, String activityName, String activityDate, TaskStatus taskStatus) {
        this.userId = userId;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.taskStatus = taskStatus;
    }

    public Long getId() {
        return activityId;
    }

    public void setId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return String.format("Activity{activityId=%d, userId=%d, activityName='%s', activityDate=%s, taskStatus=%s}", activityId, userId, activityName, activityDate, taskStatus);
    }
}
