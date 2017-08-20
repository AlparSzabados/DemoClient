package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.pojos.Activity;
import com.alpar.szabados.client.pojos.User;
import com.alpar.szabados.client.pojos.UserAndActivityWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.HostConfig.HOST;
import static com.alpar.szabados.client.utils.HostConfig.PORT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

/**
 * Manages all Activity related CRUD operations
 */
@Component
public class ActivityBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/activity/";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false);

    /**
     * Parses the received activity returned from `findUserActivities`
     *
     * @param user to return activities for
     * @return Activity array with all activities of the user
     * @throws IOException if response can't be parsed
     */
    public Activity[] getUserActivities(User user) throws IOException {
        ClientResponse response = new ActivityBean().findUserActivities(user);
        if (isOk(response)) {
            return OBJECT_MAPPER.readValue(response.getEntityInputStream(), Activity[].class);
        } else {
            return new Activity[0];
        }
    }

    /**
     * Sends find activities request to the server
     *
     * @param user to find activities for
     * @return server response and activity list on OK response
     */
    public ClientResponse findUserActivities(User user) {
        return Client.create().resource(ROOT + "findActivities/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, user);
    }

    /**
     * Sends create and update request to the server
     *
     * @param user for which the activities are created/updated for
     * @param activity containing the activity name and status
     * @return server response
     */
    public ClientResponse createOrUpdateActivity(User user, Activity activity) {
        return Client.create().resource(ROOT + "createOrUpdateActivity/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, new UserAndActivityWrapper(user, activity));
    }

    /**
     * Sends delete request to the server
     *
     * @param user who's activities will be deleted
     * @return server response
     */
    public ClientResponse deleteActivities(User user) {
        return Client.create().resource(ROOT + "deleteUserActivities/")
                     .type(APPLICATION_JSON)
                     .delete(ClientResponse.class, user);
    }
}
