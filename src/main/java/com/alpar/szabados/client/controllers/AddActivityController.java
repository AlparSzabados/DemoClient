package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.handlers.MessageFactory;
import com.alpar.szabados.client.dtos.Activity;
import com.alpar.szabados.client.dtos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.dtos.TaskStatus.NOT_COMPLETED;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

/**
 * Controller for adding new activities
 */
@ManagedBean(name = "addActivityController")
@Join(path = "/add-activity", to = "/add-activity.jsf")
public class AddActivityController {
    private ActivityBean activityBean = new ActivityBean();
    /**
     * Name of the new activity
     */
    private String activityName;

    /**
     * Sends the creation request to the ActivityBean. It creates a new User that holds the name of the current user
     * and a new Activity with the activity name defined by the user with a NOT_COMPLETED status.
     *
     * @return the response from the server
     * @throws IOException
     */
    public String createActivity() throws IOException {
        if (activityName.isEmpty()) {
            MessageFactory.info("FIELD CAN'T BE EMPTY"); return "";
        }

        ClientResponse response = activityBean.createOrUpdateActivity(new User(getSessionUserName()), new Activity(activityName, NOT_COMPLETED));
        activityName = "";// Clears the activityName field on the page
        return handleResponse(response, "SUCCESSFULLY CREATED NEW ACTIVITY");
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
