package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.dtos.Activity;
import com.alpar.szabados.client.dtos.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

/**
 * Controller managing the activity history
 */
@ManagedBean(name = "historyController")
@Join(path = "/history", to = "/activity-history.jsf")
public class ActivityHistoryController {
    /**
     * Loads all activities of the current user
     */
    private List<Activity> activities = Arrays.asList(new ActivityBean().getUserActivities(new User(getSessionUserName())));

    public ActivityHistoryController() throws IOException {
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
