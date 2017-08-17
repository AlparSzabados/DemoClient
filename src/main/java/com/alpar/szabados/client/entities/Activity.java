package com.alpar.szabados.client.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Activity {
    private long activityId;
    private long userId;
    private String activityName;
    private String activityDate;
    private TaskStatus taskStatus;

    public Activity(Long userId, String activityName, String activityDate, TaskStatus taskStatus) {
        this.userId = userId;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.taskStatus = taskStatus;
    }

    public Activity() {
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
