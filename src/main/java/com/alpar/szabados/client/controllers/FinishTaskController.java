package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class FinishTaskController {
    private String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);

    private ActivityBean activityBean = new ActivityBean();
    private Activity[] userActivities = activityBean.findUserActivities(SessionUtils.getUserName());

    private List<String> activities = Arrays.stream(userActivities)
                                            .map(Activity::getActivityName)
                                            .distinct()
                                            .collect(Collectors.toList());

    private String[] selectedActivities = Arrays.stream(userActivities)
                                                .filter(activity -> Objects.equals(activity.getActivityDate(), now))
                                                .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                                                .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);

    public String completeTask() throws IOException {
        activityBean.completeTask(selectedActivities);
        return "/activity-history.xhtml?faces-redirect=true";
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String[] getSelectedActivities() {
        return selectedActivities;
    }

    public void setSelectedActivities(String[] selectedActivities) {
        this.selectedActivities = selectedActivities;
    }
}
