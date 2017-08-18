package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;

import static com.alpar.szabados.client.beans.ActivityBean.isOk;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class UserBean {
    private static final String HOST = System.getProperty("server.host", "localhost");
    private static final String PORT = System.getProperty("server.port", "8090");
    private static final String SERVER_PATH = "http://" + HOST + ":" + PORT + "/activity/";

    public boolean createUser(User user) throws IOException {
        String path = SERVER_PATH + "createUser/";
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type(APPLICATION_JSON).put(ClientResponse.class, user);
        return isOk(response);
    }

    public boolean validateUser(User user) throws IOException {
        String path = SERVER_PATH + "validateUser/";
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type(APPLICATION_JSON).post(ClientResponse.class, user);
        return isOk(response);
    }
}
