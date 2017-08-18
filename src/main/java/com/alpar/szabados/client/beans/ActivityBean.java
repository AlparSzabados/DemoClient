package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.entities.UserAndActivityWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

import static com.alpar.szabados.client.entities.TaskStatus.COMPLETED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

public class ActivityBean {
    private static final String HOST = System.getProperty("server.host", "localhost");
    private static final String PORT = System.getProperty("server.port", "8090");
    private static final String SERVER_PATH = "http://" + HOST + ":" + PORT + "/activity/"; // TODO should work from other host also

    public Activity[] findUserActivities(User user) throws IOException {
        String path = SERVER_PATH + "findActivities/";
        WebResource webResource = Client.create().resource(path); // TODO relative path?
        ClientResponse response = webResource.type(APPLICATION_JSON).post(ClientResponse.class, user); // TODO change to POST from GET
        if (isOk(response)) {
            ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false);
            return mapper.readValue(response.getEntityInputStream(), Activity[].class);
        } else {
            return new Activity[0]; // TODO huh?
        }
    }

    public boolean completeTask(String[] selectedActivities, User user) throws IOException {
        return Arrays.stream(selectedActivities)
                     .map(selectedActivity -> new Activity(selectedActivity, COMPLETED))
                     .allMatch(activity -> createActivity(user, activity));
    }

    public boolean createActivity(User user, Activity activity) {
        UserAndActivityWrapper wrapper = new UserAndActivityWrapper(user, activity);
        String path = SERVER_PATH + "createActivity/";
        WebResource webResource = Client.create().resource(path);
        ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false);
        String valueAsString = "";
        try {
            valueAsString = mapper.writeValueAsString(wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientResponse response = webResource.type(APPLICATION_JSON).post(ClientResponse.class, valueAsString);
        return isOk(response);
    }

    public static boolean isOk(ClientResponse response) { // TODO move to helper
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
