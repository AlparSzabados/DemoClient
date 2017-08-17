package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.utils.SessionUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

// TODO make it work for spaces also
public class ActivityBean {
    private static final String HOST = System.getProperty("server.host", "localhost");
    private static final String PORT = System.getProperty("server.port", "8090");
    private static final String SERVER_PATH = "http://" + HOST + ":" + PORT + "/activity/"; // TODO should work from other host also

    public Activity[] findUserActivities(String userName) throws IOException {
        String path = SERVER_PATH + "findActivities/" + userName;
        WebResource webResource = Client.create().resource(path); // TODO relative path?
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class); // TODO change to POST from GET
        if (isOk(response)) {
            ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false);

            JSONArray myObject = response.getEntity(JSONArray.class);
            return mapper.readValue(myObject.toString(), Activity[].class);
        }
        return new Activity[0];
    }

    public boolean createActivity(String activityName, String userName) throws IOException {
        String path = SERVER_PATH + "createActivity/" + activityName + "." + userName;
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type("application/json").put(ClientResponse.class);
        return isOk(response);
    }

    public boolean completeTask(String[] selectedActivities) {
        boolean result = true;
        for (String selectedActivity : selectedActivities) {
            String path = SERVER_PATH + "completeTask/" + selectedActivity + "." + SessionUtils.getUserName();
            WebResource webResource = Client.create().resource(path);
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class);
            result = result && isOk(response);
        }
        return result;
    }

    public static boolean isOk(ClientResponse response) { // TODO move to helper
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
