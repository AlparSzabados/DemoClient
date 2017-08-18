package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.alpar.szabados.client.utils.Utils.getUserName;

@ManagedBean(name = "history")
@Join(path = "/history", to = "/activity-history.jsf")
public class ActivityHistoryController {
    private List<Activity> activities = Arrays.asList(new ActivityBean().findUserActivities(new User(getUserName())));

    public ActivityHistoryController() throws IOException {
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
