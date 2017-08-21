package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.dtos.Activity;
import com.alpar.szabados.client.dtos.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.alpar.szabados.client.dtos.TaskStatus.COMPLETED;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.LoadDataUtils.*;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

/**
 * Controller for finishing activities
 */
@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class FinishTaskController {
    private ActivityBean activityBean = new ActivityBean();
    /**
     * Loads all activities of the current user
     */
    private Activity[] userActivities = activityBean.getUserActivities(new User(getSessionUserName()));

    /**
     * Loads all activity names without duplicates
     */
    private List<String> activities = getActivityNames(userActivities);

    /**
     * Loads all tasks that have a COMPLETED status and their date equals the current date.
     */
    private String[] selectedActivities = getFinishedActivities(userActivities);

    public FinishTaskController() throws IOException {
    }

    /**
     * All activities are sent to the ActivityBean for creation in case there aren't any activities with the current date,
     * or for update to the COMPLETED status
     *
     * @return redirect to the activity-history page
     */
    public String completeTask() {
        Arrays.stream(selectedActivities)
              .map(selectedActivity -> activityBean.createOrUpdateActivity(new User(getSessionUserName()), new Activity(selectedActivity, COMPLETED)))
              .filter(response -> !isOk(response))
              .forEach(response -> handleResponse(response, null));
        return "/activity-history.xhtml";
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
