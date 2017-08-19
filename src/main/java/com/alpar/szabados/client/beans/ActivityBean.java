package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.entities.UserAndActivityWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import java.io.IOException;
import java.util.Arrays;

import static com.alpar.szabados.client.entities.TaskStatus.COMPLETED;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.HostConfig.HOST;
import static com.alpar.szabados.client.utils.HostConfig.PORT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class ActivityBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/activity/";

    public ClientResponse findUserActivities(User user) throws IOException {
        return Client.create().resource(ROOT + "findActivities/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, user);
    }

    public boolean completeTask(String[] selectedActivities, User user) throws IOException {
        return Arrays.stream(selectedActivities)
                     .map(selectedActivity -> new Activity(selectedActivity, COMPLETED))
                     .allMatch(activity -> isOk(createOrUpdateActivity(user, activity)));
    }

    public ClientResponse createOrUpdateActivity(User user, Activity activity) {
        return Client.create().resource(ROOT + "createOrUpdateActivity/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, new UserAndActivityWrapper(user, activity));
    }
}
