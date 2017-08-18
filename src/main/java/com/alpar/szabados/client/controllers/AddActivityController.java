package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

import static com.alpar.szabados.client.entities.TaskStatus.NOT_COMPLETED;
import static com.alpar.szabados.client.utils.Utils.getUserName;

@ManagedBean(name = "addActivity")
@Join(path = "/add-activity", to = "/add-activity.jsf")
public class AddActivityController {
    private String activityName;

    public String createActivity() throws IOException {
        new ActivityBean().createActivity(new User(getUserName()), new Activity(activityName, NOT_COMPLETED));
        return "/activities.xhtml?faces-redirect=true";
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
