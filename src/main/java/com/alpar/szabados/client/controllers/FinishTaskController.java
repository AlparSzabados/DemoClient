package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.alpar.szabados.client.utils.Utils.getUserName;

@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class FinishTaskController {
    private Activity[] userActivities = getUserActivities();

    private List<String> activities = getActivityNames();
    private String[] selectedActivities = getFinishedActivities();

    public FinishTaskController() throws IOException {
    }

    private Activity[] getUserActivities() throws IOException {
        return new ActivityBean().findUserActivities(new User(getUserName()));
    }

    private List<String> getActivityNames() {
        return Arrays.stream(userActivities)
                     .map(Activity::getActivityName)
                     .distinct()
                     .collect(Collectors.toList());
    }

    private String[] getFinishedActivities() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);

        return Arrays.stream(userActivities)
                     .filter(activity -> Objects.equals(activity.getActivityDate(), now))
                     .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                     .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);
    }


    public String completeTask() throws IOException {
        boolean isOk = new ActivityBean().completeTask(selectedActivities, new User(getUserName()));
        assert isOk; // TODO validate
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
