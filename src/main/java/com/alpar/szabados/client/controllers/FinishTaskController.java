package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@SessionScoped
@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class FinishTaskController {
    private List<String> activities;
    private String[] selectedActivities;

    //TODO resolve by bean?
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() throws IOException {
//        LocalDateTime date = LocalDateTime.now();
//        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ActivityBean activityBean = new ActivityBean();
        Activity[] userActivities = activityBean.findUserActivities(SessionUtils.getUserName());
        activities = Arrays.stream(userActivities).map(Activity::getActivityName).distinct().collect(Collectors.toList());
        selectedActivities = Arrays.stream(userActivities)
                                   .filter(activity -> Objects.equals(activity.getActivityDate(), "2017-07-16"))
                                   .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                                   .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);
    }

    public void completeTask() {
        ActivityBean activityBean = new ActivityBean();
        activityBean.completeTask(selectedActivities);
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
