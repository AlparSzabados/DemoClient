package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
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

@ManagedBean(name = "history")
@Join(path = "/history", to = "/activity-history.jsf")
public class ActivityHistoryController {
    private ActivityBean activityBean = new ActivityBean();
    private List<Activity> activities = Arrays.asList(activityBean.findUserActivities(SessionUtils.getUserName()));

    public ActivityHistoryController() throws IOException {
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
