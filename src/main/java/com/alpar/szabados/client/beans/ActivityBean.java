package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.entities.UserAndActivityWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

import static com.alpar.szabados.client.entities.TaskStatus.COMPLETED;
import static com.alpar.szabados.client.utils.HostConfig.HOST;
import static com.alpar.szabados.client.utils.HostConfig.PORT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

public class ActivityBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/activity/";

    public Activity[] findUserActivities(User user) throws IOException {
        ClientResponse response = Client.create().resource(ROOT + "findActivities/")
                                        .type(APPLICATION_JSON)
                                        .post(ClientResponse.class, user);
        if (isOk(response)) {
            return new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false)
                                     .readValue(response.getEntityInputStream(), Activity[].class);
        } else {
            return new Activity[0]; // FIXME
        }
    }

    public boolean completeTask(String[] selectedActivities, User user) throws IOException {
        return Arrays.stream(selectedActivities)
                     .map(selectedActivity -> new Activity(selectedActivity, COMPLETED))
                     .allMatch(activity -> createOrUpdateActivity(user, activity));
    }

    public boolean createOrUpdateActivity(User user, Activity activity) {
        try {
            String valueAsString = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false)
                                                     .writeValueAsString(new UserAndActivityWrapper(user, activity));
            ClientResponse response = Client.create().resource(ROOT + "createOrUpdateActivity/")
                                            .type(APPLICATION_JSON)
                                            .post(ClientResponse.class, valueAsString);
            return isOk(response);
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME
        }
    }

    static boolean isOk(ClientResponse response) { // TODO move to helper
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
