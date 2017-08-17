package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE;

@SessionScoped
@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class ActivitiesController {
    private List<String> activities;
    private String[] selectedActivities;

    @PostConstruct
    public void init() {
        try {
            System.out.println("zzzzzz"); // FIXME
            LocalDateTime date = LocalDateTime.now();
            String formatDate = date.format(ISO_DATE);

            Activity[] userActivities = new ActivityBean().findUserActivities(SessionUtils.getUserName());

            selectedActivities = Arrays.stream(userActivities)
                                       .filter(activity -> Objects.equals(activity.getActivityDate(), formatDate))
                                       .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                                       .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);

            activities = Arrays.stream(userActivities)
                               .map(Activity::getActivityName)
                               .distinct()
                               .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e); // FIXME
        }
    }

    public void completeTask() {
        ActivityBean activityBean = new ActivityBean();
        activityBean.completeTask(selectedActivities);
    }

    public List<String> getActivities2() { // FIXME
        System.out.println("Apeleaza");
        return null;
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
